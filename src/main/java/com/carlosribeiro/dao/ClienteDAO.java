package com.carlosribeiro.dao;

import com.carlosribeiro.model.Cliente;

import java.util.List;

public interface ClienteDAO extends DAOGenerico<Cliente> {
    List<Cliente> recuperarClientesOrdenadosPorNome();
}