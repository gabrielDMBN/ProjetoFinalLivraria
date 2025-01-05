package com.carlosribeiro.service;

import com.carlosribeiro.dao.ItemFaturadoDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.StatusIndevidoException;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.ItemFaturado;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemFaturadoService {

    private final ItemFaturadoDAO itemFaturadoDAO = FabricaDeDaos.getDAO(ItemFaturadoDAO.class);
    private final FaturaService faturaService = new FaturaService();

    public ItemFaturado incluirItemFaturado(ItemFaturado itemFaturado) {
        itemFaturadoDAO.incluir(itemFaturado);
        return itemFaturado;
    }

    public void remover(int itemFaturadoId) {
        ItemFaturado itemFaturado = itemFaturadoDAO.recuperarPorId(itemFaturadoId);
        if (itemFaturado == null) {
            throw new EntidadeNaoEncontradaException("ItemFaturado inexistente.");
        }
        ItemDePedido itemDePedido = itemFaturado.getItemDePedido();
        itemDePedido.getLivro().setQtdEstoque(itemDePedido.getLivro().getQtdEstoque() + itemFaturado.getQtdFaturada());
        itemDePedido.getItensFaturados().remove(itemFaturado);
        itemFaturadoDAO.remover(itemFaturadoId);
    }

    public List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId) {
        return itemFaturadoDAO.recuperarItensFaturadosPorItemDePedido(itemDePedidoId);
    }

    public boolean faturarPedido(ItemDePedido itemDePedido) {

        // checar status pra ver se pode faturar pela primeira vez ou novamenet
        if ("Cancelado".equals(itemDePedido.getPedido().getStatus())) {
            //throw new StatusIndevidoException("O pedido está cancelado e não pode ser faturado.");
            System.out.println("O pedido está cancelado e não pode ser faturado.");
            return false;
        }
        if ("Faturado".equals(itemDePedido.getPedido().getStatus())) {
            //throw new StatusIndevidoException("O pedido já foi faturado.");
            System.out.println("O pedido já foi faturado.");
            return false;
        }

        boolean algumItemFaturado = false;
        boolean faltaEstoque = false;
        List<ItemFaturado> itensFaturados = new ArrayList<>();
        double valorTotalFatura = 0;

        // ffaturar todos os itens do pedido
        for (ItemDePedido item : itemDePedido.getPedido().getItensDePedido()) {
            if (item.getQtdAFaturar() > 0) {
                ItemFaturado itemFaturado = faturarItemDePedido(item);
                if (itemFaturado != null) {
                    itensFaturados.add(itemFaturado);
                    valorTotalFatura += itemFaturado.getQtdFaturada() * item.getLivro().getPreco();
                    algumItemFaturado = true;
                } else {
                    faltaEstoque = true;
                }
            }
        }

        // criar uma única fatura se algum item foi Faturado
        if (algumItemFaturado) {
            Fatura fatura = new Fatura(LocalDate.now(), null, itemDePedido.getPedido().getCliente(), valorTotalFatura, 0);
            fatura.setItensFaturados(itensFaturados);
            faturaService.incluir(fatura);

            for (ItemFaturado item : itensFaturados) {
                item.setFatura(fatura);
            }

            // atualizar o status do pedido se todos os itens foram faturados
            if (itemDePedido.getPedido().getItensDePedido().stream().allMatch(item -> item.getQtdAFaturar() == 0)) {
                itemDePedido.getPedido().setStatus("Faturado");
            }

            System.out.println("Pedido faturado com sucesso!");
        } else if (faltaEstoque) {
           System.out.println("Algum item não foi faturado devido à falta de estoque.");
        }
//        else {
//          // System.out.println("Todos os itens do pedido já foram faturados.");
//        }

        return algumItemFaturado;
    }

    private ItemFaturado faturarItemDePedido(ItemDePedido itemDePedido) {
        int qtdAFaturar = itemDePedido.getQtdAFaturar();
        int qtdFaturada = Math.min(qtdAFaturar, itemDePedido.getLivro().getQtdEstoque());

        if (qtdFaturada > 0) {
            ItemFaturado itemFaturado = new ItemFaturado(qtdFaturada, itemDePedido, null);
            incluirItemFaturado(itemFaturado);
            itemDePedido.getLivro().setQtdEstoque(itemDePedido.getLivro().getQtdEstoque() - qtdFaturada);
            itemDePedido.setQtdAFaturar(qtdAFaturar - qtdFaturada);

            itemDePedido.getItensFaturados().add(itemFaturado);

            if (itemDePedido.getQtdAFaturar() > 0) {
                System.out.println("Ainda falta faturar " + itemDePedido.getQtdAFaturar() + " unidades do livro '" + itemDePedido.getLivro().getTitulo() + "' por falta de estoque.");
            }
            return itemFaturado;
        }
//        else if (qtdAFaturar > 0) {
//            //return null;
//            //System.out.println("Não foi possível faturar o livro '" + itemDePedido.getLivro().getTitulo() + "' por falta de estoque.");
//        }
        return null;
    }

}