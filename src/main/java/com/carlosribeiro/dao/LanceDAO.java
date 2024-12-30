package com.carlosribeiro.dao;

import com.carlosribeiro.model.Lance;
import com.carlosribeiro.model.Produto;

import java.util.List;

public interface LanceDAO extends DAOGenerico<Lance>{
    List<Lance> recuperarTodosOsLancesDeUmProduto(int id);
}
