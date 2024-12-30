package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.PedidoDAO;
import com.carlosribeiro.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDaoImpl extends DAOGenericoImpl<Pedido> implements PedidoDAO {

    public List<Pedido> recuperarTodosOsPedidosDeUmCliente(int id) {
        return map.values()
                .stream()
                .filter(pedido -> pedido.getCliente().getId() == id)
                .collect(Collectors.toList());
    }
}