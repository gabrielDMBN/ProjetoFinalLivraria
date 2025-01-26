package com.carlosribeiro.service;

import com.carlosribeiro.dao.ItemFaturadoDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.StatusIndevidoException;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.ItemFaturado;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.util.FabricaDeDaos;
import com.carlosribeiro.exception.NenhumItemFaturadoException;

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

    public void reestocar(int itemFaturadoId){
        ItemFaturado itemFaturado = itemFaturadoDAO.recuperarPorId(itemFaturadoId);
        if (itemFaturado == null) {
            throw new EntidadeNaoEncontradaException("ItemFaturado inexistente.");
        }
        ItemDePedido itemDePedido = itemFaturado.getItemDePedido();
        itemDePedido.getLivro().setQtdEstoque(itemDePedido.getLivro().getQtdEstoque() + itemFaturado.getQtdFaturada());


    }

    public List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId) {
        return itemFaturadoDAO.recuperarItensFaturadosPorItemDePedido(itemDePedidoId);
    }

    public Fatura faturarPedido(Pedido pedido) {

        if ("Cancelado".equals(pedido.getStatus())) {
            throw new StatusIndevidoException("O pedido foi cancelado.");
        }
        if ("Faturado".equals(pedido.getStatus())) {
            throw new StatusIndevidoException("O pedido já foi faturado.");
        }

        boolean algumItemFaturado = false;
        boolean faltaEstoque = false;
        List<ItemFaturado> itensFaturados = new ArrayList<>();
        double valorTotalFatura = 0;

        for (ItemDePedido item : pedido.getItensDePedido()) {
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

        if (algumItemFaturado) {
            Fatura fatura = new Fatura(LocalDate.now(), null, pedido.getCliente(), valorTotalFatura, 0);
            fatura.setItensFaturados(itensFaturados);
            faturaService.incluir(fatura);

            for (ItemFaturado item : itensFaturados) {
                item.setFatura(fatura);
            }

            if (pedido.getItensDePedido().stream().allMatch(item -> item.getQtdAFaturar() == 0)) {
                pedido.setStatus("Faturado");
            } else {
                pedido.setStatus("Parcialmente Faturado");
            }
            return fatura;
        }
        else {
            throw new NenhumItemFaturadoException("Nenhum item faturado. (Falta de estoque)");
        }

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

            return itemFaturado;
        }
        return null;
    }

//    public String relatorioUmResumo(ItemFaturado itemDePedido) {
//        return "Livro = " + itemDePedido.getItemDePedido().getLivro().getTitulo() +
//                "  Quantidade Faturada = " + itemDePedido.getQtdFaturada() + "  Data da Fatura = " + itemDePedido.getFatura().getDataEmissao();
//    }

}