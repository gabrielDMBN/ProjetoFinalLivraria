package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.ItemDePedidoDAO;
import com.carlosribeiro.model.ItemDePedido;

import java.util.List;

public class ItemDePedidoDaoImpl extends DAOGenericoImpl<ItemDePedido> implements ItemDePedidoDAO {
    public List<ItemDePedido> recuperarItensDePedidoPorPedido(int id) {
        return map.values()
                  .stream()
                  .filter((item) -> item.getPedido().getId() == id)
                  .toList();
    }
}
