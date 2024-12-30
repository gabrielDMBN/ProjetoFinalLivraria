package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.ClienteDAO;
import com.carlosribeiro.model.Cliente;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteDaoImpl extends DAOGenericoImpl<Cliente> implements ClienteDAO {
    @Override
    public List<Cliente> recuperarClientesOrdenadosPorNome() {
        return map.values()
                .stream()
                .sorted((c1, c2) -> c1.getNome().compareTo(c2.getNome()))
                .collect(Collectors.toList());
    }
}