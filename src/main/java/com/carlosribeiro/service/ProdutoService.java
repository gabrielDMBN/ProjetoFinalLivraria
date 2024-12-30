package com.carlosribeiro.service;

import com.carlosribeiro.dao.ProdutoDAO;
import com.carlosribeiro.dao.impl.ProdutoDaoImpl;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.ProdutoComLancesException;
import com.carlosribeiro.model.Produto;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.LinkedHashMap;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO = FabricaDeDaos.getDAO(ProdutoDAO.class);

    public Produto incluir(Produto produto) {
        return produtoDAO.incluir(produto);
    }

    // O nome do produto pode ser alterado a qq momento
    public Produto alterar(Produto produto, String novoNome) {
        produto.setNome(novoNome);
        return produto;
    }

    // O lance mínimo de um produto só pode ser alterado caso ainda
    // não tenha sido emitido nenhum lance para o produto.
    public Produto alterar(Produto produto, double novoLanceMinimo) {
        if(produto.getLances().isEmpty()) {
            produto.setLanceMinimo(novoLanceMinimo);
        }
        else {
            throw new ProdutoComLancesException(
                "Não é possível alterar o lance mínimo de um produto que possui lances");
        }
        return produto;
    }

    // Verificar se o produto existe
    // Só permitir a remoção do produto caso ele não tenha lances
    public Produto remover(int id) {
        Produto produto = recuperarProdutoPorId(id);
        if (produto == null) {
            throw new EntidadeNaoEncontradaException("Produto inexistente.");
        }
        if (produto.getLances().isEmpty()) {
            produtoDAO.remover(produto.getNumero());
        } else {
            throw new ProdutoComLancesException(
                    "Este produto possui lances e não pode ser removido.");
        }
        return produto;
    }

    public Produto recuperarProdutoPorId(int id) {
        Produto produto = produtoDAO.recuperarPorId(id);
        if (produto == null)
            throw new EntidadeNaoEncontradaException("Produto inexistente.");
        return produto;
    }

    public List<Produto> recuperarProdutos() {
        return produtoDAO.recuperarTodos();
    }

    public List<Produto> recuperarProdutosOrdenadosPorNome() {
        return produtoDAO.recuperarProdutosOrdenadosPorNome();
    }
}
