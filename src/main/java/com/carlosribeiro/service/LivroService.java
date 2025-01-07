package com.carlosribeiro.service;

import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.ItemComDependenciasException;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.List;

public class LivroService {

    private final LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);

    public Livro incluir(Livro livro) {
        return livroDAO.incluir(livro);
    }

    public Livro alterarIsbn(int id, String novoIsbn) {
        Livro livro = recuperarLivroPorId(id);
        livro.setIsbn(novoIsbn);
        return livro;
    }

    public Livro alterarTitulo(int id, String novoTitulo) {
        Livro livro = recuperarLivroPorId(id);
        livro.setTitulo(novoTitulo);
        return livro;
    }

    public Livro alterarDescricao(int id, String novaDescricao) {
        Livro livro = recuperarLivroPorId(id);
        livro.setDescricao(novaDescricao);
        return livro;
    }

    public Livro alterarQtdEstoque(int id, int novaQtdEstoque) {
        Livro livro = recuperarLivroPorId(id);
        livro.setQtdEstoque(novaQtdEstoque);
        return livro;
    }

    public Livro alterarPreco(int id, double novoPreco) {
        Livro livro = recuperarLivroPorId(id);
        livro.setPreco(novoPreco);
        return livro;
    }

    public Livro remover(int id) {
        Livro livro = recuperarLivroPorId(id);
        if (livro == null) {
            throw new EntidadeNaoEncontradaException("Livro inexistente.");
        }
        if (!livro.getItemDePedidos().isEmpty()) {
            //throw new EntidadeNaoEncontradaException("Não é possível remover o livro pois ele está associado a um ou mais itens de pedido.");
            throw new ItemComDependenciasException("Não é possível remover o livro pois ele está associado a um ou mais itens de pedido.");
//            System.out.println("Aviso: Não é possível remover o livro pois ele está associado a um ou mais itens de pedido.");
//            return livro;
        }
        livroDAO.remover(livro.getId());
        System.out.println("Livro removido com sucesso!");
        return livro;
    }

    public Livro recuperarLivroPorId(int id) {
        Livro livro = livroDAO.recuperarPorId(id);
        if (livro == null)
            throw new EntidadeNaoEncontradaException("Livro inexistente.");
        return livro;
    }

    public List<Livro> recuperarLivros() {
        return livroDAO.recuperarTodos();
    }

    public List<Livro> recuperarLivrosOrdenadosPorTitulo() {
        return livroDAO.recuperarLivrosOrdenadosPorNome();
    }
}