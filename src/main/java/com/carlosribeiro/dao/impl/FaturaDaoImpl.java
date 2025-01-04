package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.model.Fatura;

import java.util.List;
import java.util.stream.Collectors;

public class FaturaDaoImpl extends DAOGenericoImpl<Fatura> implements FaturaDAO {

    public List<Fatura> recuperarTodasAsFaturasDeUmCliente(int id) {
        return map.values()
                .stream()
                .filter(fatura -> fatura.getCliente().getId() == id)
                .collect(Collectors.toList());
    }
}