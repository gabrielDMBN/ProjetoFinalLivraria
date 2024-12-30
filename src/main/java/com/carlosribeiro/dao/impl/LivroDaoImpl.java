package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.model.Livro;

import java.util.List;
import java.util.stream.Collectors;

public class LivroDaoImpl extends DAOGenericoImpl<Livro> implements LivroDAO {
    @Override
    public List<Livro> recuperarLivrosOrdenadosPorNome() {
        return map.values()
                .stream()
                .sorted((l1, l2) -> l1.getTitulo().compareTo(l2.getTitulo()))
                .collect(Collectors.toList());
    }
}