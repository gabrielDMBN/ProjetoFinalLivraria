package com.carlosribeiro.dao;

import com.carlosribeiro.model.Fatura;

import java.util.List;

public interface FaturaDAO extends DAOGenerico<Fatura> {
    List<Fatura> recuperarTodasAsFaturasDeUmCliente(int id);
}