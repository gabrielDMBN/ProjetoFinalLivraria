package com.carlosribeiro.service;

import com.carlosribeiro.dao.LanceDAO;
import com.carlosribeiro.dao.impl.LanceDaoImpl;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.ValorDeLanceInvalidoException;
import com.carlosribeiro.model.Lance;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.List;

public class LanceService {

    private final LanceDAO lanceDAO = FabricaDeDaos.getDAO(LanceDAO.class);

    // Caso seja o primeiro lance do produto, o valor tem que ser maior ou igual
    // ao valor do lance mínimo.
    // Caso não seja o primeiro lance do produto, o valor tem que maior do que o
    // valor do último lance.
    // Em ambos os casos é preciso salvar em produto o valor do último lance.
    // E é preciso adicionar o lance na lista de lances do produto.
    public Lance incluir(Lance lance) {
        if (lance.getProduto().getLances().isEmpty()) {
            if (lance.getValor() < lance.getProduto().getLanceMinimo()) {
                throw new ValorDeLanceInvalidoException(
                    "O valor do lance deve ser maior ou igual ao valor do lance mínimo (" +
                        lance.getProduto().getLanceMinimoMasc() + ")");
            }
        }
        else {
            if (lance.getValor() <= lance.getProduto().getValorUltimoLance()) {
                throw new ValorDeLanceInvalidoException(
                    "O valor do lance deve ser maior do que o valor do último lance (" +
                        lance.getProduto().getValorUltimoLanceMasc() + ")");
            }
        }
        lanceDAO.incluir(lance);
        lance.getProduto().setValorUltimoLance(lance.getValor());
        lance.getProduto().getLances().add(lance);
        return lance;
    }

    // Deve dar erro caso o lance a ser removido não seja encontrado.
    // O lance deve ser removido da lista de lances do produto
    // Se o lance removido for o último lance de um produto, o valor do último
    // lance deve ser atualizado para zero.
    // Caso o lance removido não seja o último lance do produto, é preciso
    // atualizar o valor do último lance.
    public Lance remover(int id) {
        Lance lance = this.recuperarLancePorId(id);
        lance.getProduto().getLances().remove(lance);
        if (lance.getProduto().getLances().isEmpty()) {
            lance.getProduto().setValorUltimoLance(0);
        }
        else {
            int qtdLances = lance.getProduto().getLances().size();
            Lance ultimoLance = lance.getProduto().getLances().get(qtdLances - 1);
            lance.getProduto().setValorUltimoLance(ultimoLance.getValor());
        }
        lanceDAO.remover(id);
        return lance;
    }

    // Deve retornar um erro caso o lance não seja encontrado.
    public Lance recuperarLancePorId(int id) {
        Lance lance = lanceDAO.recuperarPorId(id);
        if (lance == null)
            throw new EntidadeNaoEncontradaException("Lance inexistente.");
        return lance;
    }

    public List<Lance> recuperarLances() {
        return lanceDAO.recuperarTodos();
    }

    public List<Lance> recuperarTodosOsLancesDeUmProduto(int id) {
        return lanceDAO.recuperarTodosOsLancesDeUmProduto(id);
    }
}
