package com.carlosribeiro.dao;

import com.carlosribeiro.model.Pedido;

import java.util.List;

public interface PedidoDAO extends DAOGenerico<Pedido> {
    List<Pedido> recuperarTodosOsPedidosDeUmCliente(int id);
}