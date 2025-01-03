package com.carlosribeiro.service;

import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.dao.ItemFaturadoDAO;
import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.ItemFaturado;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemFaturadoService {

    private final ItemFaturadoDAO itemFaturadoDAO = FabricaDeDaos.getDAO(ItemFaturadoDAO.class);
    private final LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);
    private final FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);

    public ItemFaturado incluirItemFaturado(ItemFaturado itemFaturado) {
        itemFaturadoDAO.incluir(itemFaturado);
        return itemFaturado;
    }

    public void remover(int itemFaturadoId) {
        ItemFaturado itemFaturado = itemFaturadoDAO.recuperarPorId(itemFaturadoId);
        if (itemFaturado == null) {
            throw new IllegalArgumentException("Item faturado inexistente.");
        }
        Livro livro = itemFaturado.getItemDePedido().getLivro();
        livro.setQtdEstoque(livro.getQtdEstoque() + itemFaturado.getQtdFaturada());
        itemFaturadoDAO.remover(itemFaturadoId);
    }

    public List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId) {
        return itemFaturadoDAO.recuperarItensFaturadosPorItemDePedido(itemDePedidoId);
    }

    public boolean faturarPedido(Pedido pedido) {
        boolean algumItemFaturado = false;
        List<ItemFaturado> itensFaturados = new ArrayList<>();
        double valorTotalFatura = 0;
        double valorDescontadoFatura = 0;

        for (ItemDePedido itemDePedido : pedido.getItensDePedido()) {
            ItemFaturado itemFaturado = faturarItemDePedido(itemDePedido);
            if (itemFaturado != null) {
                itensFaturados.add(itemFaturado);
                valorTotalFatura += itemFaturado.getQtdFaturada() * itemDePedido.getLivro().getPreco();
                valorDescontadoFatura += itemFaturado.getQtdFaturada() * 0.05;
                algumItemFaturado = true;
            }
        }

        if (algumItemFaturado) {
            Fatura fatura = new Fatura(LocalDate.now(), null, pedido.getCliente(), valorTotalFatura, valorDescontadoFatura);
            fatura.setItensFaturados(itensFaturados);
            faturaDAO.incluir(fatura);

            for (ItemFaturado itemFaturado : itensFaturados) {
                itemFaturado.setFatura(fatura);
                //itemFaturadoDAO.atualizar(itemFaturado);
            }
        }

        return algumItemFaturado;
    }

    private ItemFaturado faturarItemDePedido(ItemDePedido itemDePedido) {
        Livro livro = itemDePedido.getLivro();
        int qtdAFaturar = itemDePedido.getQtdAFaturar();
        int qtdFaturada = Math.min(qtdAFaturar, livro.getQtdEstoque());

        if (qtdFaturada > 0) {
            ItemFaturado itemFaturado = new ItemFaturado(qtdFaturada, itemDePedido, null);
            incluirItemFaturado(itemFaturado);
            livro.setQtdEstoque(livro.getQtdEstoque() - qtdFaturada);
            itemDePedido.setQtdAFaturar(qtdAFaturar - qtdFaturada);

            if (itemDePedido.getQtdAFaturar() > 0) {
                System.out.println("Ainda falta faturar " + itemDePedido.getQtdAFaturar() + " unidades do livro " + livro.getTitulo() + " por falta de estoque.");
            }
            return itemFaturado;
        }
        return null;
    }
}