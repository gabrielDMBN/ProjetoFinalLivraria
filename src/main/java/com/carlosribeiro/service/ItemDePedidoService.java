package com.carlosribeiro.service;

import com.carlosribeiro.dao.ItemDePedidoDAO;
import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.List;

public class ItemDePedidoService {

    private final ItemDePedidoDAO itemDePedidoDAO = FabricaDeDaos.getDAO(ItemDePedidoDAO.class);
    private final LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);

    public ItemDePedido incluirItemDePedido(Pedido pedido, Livro livro, int quantidade) {
        if (livro.getQtdEstoque() < quantidade) {
            throw new IllegalArgumentException("Quantidade de estoque insuficiente.");
        }
        ItemDePedido itemDePedido = new ItemDePedido(quantidade, quantidade, quantidade, livro.getPreco(), pedido, livro);
        livro.setQtdEstoque(livro.getQtdEstoque() - quantidade);
        itemDePedidoDAO.incluir(itemDePedido);
        return itemDePedido;
    }

    public List<Livro> listarLivros() {
        return livroDAO.recuperarTodos();
    }

    public List<ItemDePedido> recuperarItensDePedidoPorPedido(int pedidoId) {
        return itemDePedidoDAO.recuperarItensDePedidoPorPedido(pedidoId);
    }
}