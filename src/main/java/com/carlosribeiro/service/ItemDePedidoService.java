package com.carlosribeiro.service;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.TentativaAcessoIndevidoException;
import com.carlosribeiro.exception.ItemComDependenciasException;
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
    private final PedidoService pedidoService = new PedidoService();

    public ItemDePedido incluirItemDePedido(Pedido pedido, Livro livro, int quantidade) {
        ItemDePedido itemDePedido = new ItemDePedido(quantidade, quantidade, quantidade, livro.getPreco(), pedido, livro);
        itemDePedidoDAO.incluir(itemDePedido);
        livro.getItemDePedidos().add(itemDePedido);
        return itemDePedido;
    }

    public void remover(int itemDePedidoId, int pedidoId) {
        ItemDePedido itemDePedido = itemDePedidoDAO.recuperarPorId(itemDePedidoId);
        if (itemDePedido == null) {
            throw new EntidadeNaoEncontradaException("Item de pedido não encontrado.");
        }
        if (itemDePedido.getPedido().getId() != pedidoId) {
            throw new TentativaAcessoIndevidoException("O item de pedido não pertence ao pedido informado.");
        }
        if (!itemDePedido.getItensFaturados().isEmpty()) {
            throw new ItemComDependenciasException("Não é possível remover um item de pedido que já foi faturado.");
        }
        itemDePedidoDAO.remover(itemDePedidoId);
        itemDePedido.getLivro().getItemDePedidos().remove(itemDePedido); // Remove from the list in Livro
    }


    public List<Livro> listarLivros() {
        return livroDAO.recuperarTodos();
    }

    public List<ItemDePedido> recuperarItensDePedidoPorPedido(int pedidoId) {
        return itemDePedidoDAO.recuperarItensDePedidoPorPedido(pedidoId);
    }
}