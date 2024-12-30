package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.LanceDAO;
import com.carlosribeiro.model.Lance;
import com.carlosribeiro.model.Produto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LanceDaoImpl extends DAOGenericoImpl<Lance> implements LanceDAO {
    public List<Lance> recuperarTodosOsLancesDeUmProduto(int id) {
        return map.values()
                  .stream()
                  .filter((lance) -> lance.getProduto().getNumero() == id)
                  .toList();
    }
}
