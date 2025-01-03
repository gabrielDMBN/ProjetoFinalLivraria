package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.ItemFaturadoDAO;
import com.carlosribeiro.model.ItemFaturado;

import java.util.List;

public class ItemFaturadoDaoImpl extends DAOGenericoImpl<ItemFaturado> implements ItemFaturadoDAO {
    public List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId) {
        return map.values()
                .stream()
                .filter((item) -> item.getItemDePedido().getId() == itemDePedidoId)
                .toList();
    }
}