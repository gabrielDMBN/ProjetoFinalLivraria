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
    private final PedidoService pedidoService = new PedidoService();

    public ItemDePedido incluirItemDePedido(Pedido pedido, Livro livro, int quantidade) {
        ItemDePedido itemDePedido = new ItemDePedido(quantidade, quantidade, quantidade, livro.getPreco(), pedido, livro);
        itemDePedidoDAO.incluir(itemDePedido);
        return itemDePedido;
    }

    public void remover(int itemDePedidoId, int pedidoId) {
        ItemDePedido itemDePedido = itemDePedidoDAO.recuperarPorId(itemDePedidoId);
        if (itemDePedido == null) {
            throw new IllegalArgumentException("Item de pedido inexistente.");
        }
        if (itemDePedido.getPedido().getId() != pedidoId) {
            throw new IllegalArgumentException("Item de pedido não pertence ao pedido especificado.");
        }
        if (!itemDePedido.getItensFaturados().isEmpty()) {
            throw new IllegalArgumentException("Não é possível remover o item de pedido. Ele está em faturamento.");
        }
        itemDePedidoDAO.remover(itemDePedidoId);
    }

    public void listarPedidosResumidos(int clienteId) {
        List<Pedido> pedidos = pedidoService.recuperarTodosOsPedidosDeUmCliente(clienteId);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para o cliente ID: " + clienteId);
        } else {
            System.out.println("Pedidos do cliente ID: " + clienteId);
            for (Pedido pedido : pedidos) {
                System.out.println("Pedido ID: " + pedido.getId() + " | Status: " + pedido.getStatus());
                for (ItemDePedido item : pedido.getItensDePedido()) {
                    System.out.println("  - Livro: " + item.getLivro().getTitulo() + " | Preço: " + item.getLivro().getPreco() + " | Quantidade: " + item.getQtdPedida());
                }
                System.out.println("----------------------------------------");
            }
        }
    }

    public List<Livro> listarLivros() {
        return livroDAO.recuperarTodos();
    }

    public List<ItemDePedido> recuperarItensDePedidoPorPedido(int pedidoId) {
        return itemDePedidoDAO.recuperarItensDePedidoPorPedido(pedidoId);
    }
}