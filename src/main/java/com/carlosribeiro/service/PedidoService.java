package com.carlosribeiro.service;

import com.carlosribeiro.dao.PedidoDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);

    public Pedido incluir(Pedido pedido) {
        pedido.setDataEmissao(LocalDate.now());
        pedido.setDataCancelamento("Não Cancelado");
        pedido.setStatus("Processando");
        return pedidoDAO.incluir(pedido);
    }

    public Pedido cancelarPedido(int id, int clienteId) {
        Pedido pedido = recuperarPedidoPorId(id);
        if (pedido.getCliente().getId() != clienteId) {
            throw new EntidadeNaoEncontradaException("Este pedido não pertence a este cliente.");
        }
        if (pedido.getStatus().equals("Cancelado")) {
            throw new EntidadeNaoEncontradaException("Este pedido já foi cancelado.");
        }
        pedido.setDataCancelamento(LocalDate.now().toString());
        pedido.setStatus("Cancelado");
        return pedido;
    }

    public Pedido alterarStatus(int id, String novoStatus) {
        Pedido pedido = recuperarPedidoPorId(id);
        pedido.setStatus(novoStatus);
        return pedido;
    }

    public Pedido remover(int id) {
        Pedido pedido = recuperarPedidoPorId(id);
        if (pedido == null) {
            throw new EntidadeNaoEncontradaException("Pedido inexistente.");
        }
        pedidoDAO.remover(pedido.getId());
        return pedido;
    }

    public Pedido recuperarPedidoPorId(int id) {
        Pedido pedido = pedidoDAO.recuperarPorId(id);
        if (pedido == null)
            throw new EntidadeNaoEncontradaException("Pedido inexistente.");
        return pedido;
    }

    public List<Pedido> recuperarPedidos() {
        return pedidoDAO.recuperarTodos();
    }

    public List<Pedido> recuperarTodosOsPedidosDeUmCliente(int clienteId) {
        return pedidoDAO.recuperarTodosOsPedidosDeUmCliente(clienteId);
    }
}