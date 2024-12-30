package com.carlosribeiro.dao;

import com.carlosribeiro.model.Livro;

import java.util.List;

public interface LivroDAO extends DAOGenerico<Livro> {
    List<Livro> recuperarLivrosOrdenadosPorNome();
}