package com.carlosribeiro.dao;

import com.carlosribeiro.model.ItemDePedido;

import java.util.List;

public interface ItemDePedidoDAO extends DAOGenerico<ItemDePedido> {
    List<ItemDePedido> recuperarItensDePedidoPorPedido(int id);
}
