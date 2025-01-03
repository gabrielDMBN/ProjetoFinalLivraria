package com.carlosribeiro.service;

import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.List;

public class FaturaService {

    private final FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);

    public Fatura incluir(Fatura fatura) {
        fatura.setDataEmissao(LocalDate.now());
        return faturaDAO.incluir(fatura);
    }

    public Fatura cancelarFatura(int id) {
        Fatura fatura = recuperarFaturaPorId(id);
        if (fatura.getDataCancelamento() != null) {
            throw new IllegalStateException("Esta fatura já foi cancelada.");
        }
        fatura.setDataCancelamento(LocalDate.now());
        return fatura;
    }

    public Fatura remover(int id) {
        Fatura fatura = recuperarFaturaPorId(id);
        if (fatura == null) {
            throw new IllegalArgumentException("Fatura inexistente.");
        }
        faturaDAO.remover(fatura.getId());
        return fatura;
    }

    public Fatura recuperarFaturaPorId(int id) {
        Fatura fatura = faturaDAO.recuperarPorId(id);
        if (fatura == null) {
            throw new EntidadeNaoEncontradaException("Fatura inexistente.");
        }
        return fatura;
    }

    public List<Fatura> recuperarFaturas() {
        return faturaDAO.recuperarTodos();
    }

    public List<Fatura> recuperarTodasAsFaturasDeUmCliente(int clienteId) {
        return faturaDAO.recuperarTodasAsFaturasDeUmCliente(clienteId);
    }
}