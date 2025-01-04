package com.carlosribeiro.service;

import com.carlosribeiro.dao.ItemFaturadoDAO;
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
            throw new IllegalArgumentException("Item faturado inexistente.");
        }
        ItemDePedido itemDePedido = itemFaturado.getItemDePedido();
        itemDePedido.getLivro().setQtdEstoque(itemDePedido.getLivro().getQtdEstoque() + itemFaturado.getQtdFaturada());

        // Remove the ItemFaturado from the list in ItemDePedido
        itemDePedido.getItensFaturados().remove(itemFaturado);

        itemFaturadoDAO.remover(itemFaturadoId);
    }

    public List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId) {
        return itemFaturadoDAO.recuperarItensFaturadosPorItemDePedido(itemDePedidoId);
    }

    public boolean faturarPedido(ItemDePedido itemDePedido) {
        // Debug statement to check if the method is called multiple times
        System.out.println("faturarPedido method called for Pedido ID: " + itemDePedido.getPedido().getId());

        // Check if the order is canceled
        if ("Cancelado".equals(itemDePedido.getPedido().getStatus())) {
            System.out.println("O pedido está cancelado e não pode ser faturado.");
            return false;
        }

        boolean algumItemFaturado = false;
        boolean faltaEstoque = false;
        boolean todosItensFaturados = true;
        List<ItemFaturado> itensFaturados = new ArrayList<>();
        double valorTotalFatura = 0;

        // Faturar todos os itens do pedido
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
            } else {
                todosItensFaturados &= item.getQtdAFaturar() == 0;
            }
        }

        // Criar uma única fatura se algum item foi faturado
        if (algumItemFaturado) {
            Fatura fatura = new Fatura(LocalDate.now(), null, itemDePedido.getPedido().getCliente(), valorTotalFatura, 0);
            fatura.setItensFaturados(itensFaturados);
            faturaService.incluir(fatura);

            for (ItemFaturado item : itensFaturados) {
                item.setFatura(fatura);
            }
        } else if (todosItensFaturados) {
            System.out.println("Todos os itens do pedido já foram faturados.");
        } else if (faltaEstoque) {
            System.out.println("Nenhum item foi faturado devido à falta de estoque.");
        }

        // Atualizar o status do pedido se todos os itens foram faturados
        if (todosItensFaturados) {
            itemDePedido.getPedido().setStatus("Faturado");
        }

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
                System.out.println("Ainda falta faturar " + itemDePedido.getQtdAFaturar() + " unidades do livro " + itemDePedido.getLivro().getTitulo() + " por falta de estoque.");
            }
            return itemFaturado;
        }
        return null;
    }
}