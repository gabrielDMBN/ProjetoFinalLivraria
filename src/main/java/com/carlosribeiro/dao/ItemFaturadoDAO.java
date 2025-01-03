package com.carlosribeiro.dao;

import com.carlosribeiro.model.ItemFaturado;

import java.util.List;

public interface ItemFaturadoDAO extends DAOGenerico<ItemFaturado> {
    List<ItemFaturado> recuperarItensFaturadosPorItemDePedido(int itemDePedidoId);
}
