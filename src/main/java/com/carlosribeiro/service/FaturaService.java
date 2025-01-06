package com.carlosribeiro.service;

import com.carlosribeiro.dao.ClienteDAO;
import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.model.ItemFaturado;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.List;


public class FaturaService {

    private final FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);
    private final ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
    private static final ItemFaturadoService itemFaturadoService = new ItemFaturadoService();

    public Fatura incluir(Fatura fatura) {
        fatura.setDataEmissao(LocalDate.now());

        // aplica 5% de desconto quando tem 4 ou mais faturas nao canceladas na conta
        List<Fatura> faturasCliente = faturaDAO.recuperarTodasAsFaturasDeUmCliente(fatura.getCliente().getId());
        long faturasNaoCanceladas = faturasCliente.stream()
                .filter(f -> f.getDataCancelamento() == null)
                .count();

        if (faturasNaoCanceladas >= 4) {
            System.out.println("O cliente possui " + faturasNaoCanceladas + " faturas não canceladas, logo, terá um desconto de 5%.");
            double valorTotal = fatura.getValorTotalFatura();
            double desconto = valorTotal * 0.05;
            fatura.setValorDescontadoFatura(desconto);
            fatura.setValorTotalFatura(valorTotal - desconto);
        } else {
            fatura.setValorDescontadoFatura(0);
        }
        Fatura novaFatura = faturaDAO.incluir(fatura);
        Cliente cliente = clienteDAO.recuperarPorId(fatura.getCliente().getId());
        cliente.setQtdFaturas(cliente.getQtdFaturas() + 1);
        return novaFatura;
    }

    public Fatura cancelarFatura(int id, int clienteId) {
        Fatura fatura = recuperarFaturaPorId(id);
        if (fatura.getCliente().getId() != clienteId) {
            System.out.println("A fatura não pertence ao cliente especificado.");
            return fatura;
        }

        if (fatura.getDataCancelamento() != null) {
            System.out.println("Esta fatura já foi cancelada.");
            return fatura;
        }

        List<Fatura> faturasCliente = faturaDAO.recuperarTodasAsFaturasDeUmCliente(clienteId);
        long faturasNaoCanceladas = faturasCliente.stream()
                .filter(f -> f.getDataCancelamento() == null)
                .count();

        //so permite cancelarr uma fatura se ele tiver mais d 3 faturas nao canceladas na conta
        if (faturasNaoCanceladas < 1) {
            System.out.println("Não é possível cancelar a fatura. O cliente possui apenas " + faturasNaoCanceladas + " faturas não canceladas.");
            return fatura;
        }

        fatura.setDataCancelamento(LocalDate.now());

        ////devolver estoque (ja realizado pelo reestocar de itens faturados!
        for (ItemFaturado item : fatura.getItensFaturados()) {
            itemFaturadoService.reestocar(item.getId());
        }
        fatura.getItensFaturados().clear();

        //System.out.println("Fatura cancelada com sucesso!");
        return fatura;
    }

    public void remover(int id, int clienteId) {
        Fatura fatura = recuperarFaturaPorId(id);
        if (fatura == null) {
            throw new EntidadeNaoEncontradaException("Fatura inexistente.");
        }

        if (fatura.getCliente().getId() != clienteId) {
            System.out.println("A fatura não pertence ao cliente especificado.");
            return;
        }

        if (fatura.getDataCancelamento() != null) {
            System.out.println("Não é possível remover a fatura. A fatura já está cancelada.");
            return;
        }

        // Remover itens faturados e devolver estoque
        for (ItemFaturado item : fatura.getItensFaturados()) {
            itemFaturadoService.remover(item.getId());
        }
        fatura.getItensFaturados().clear();

        faturaDAO.remover(fatura.getId());
        System.out.println("Fatura removida com sucesso.");
    }

    public Fatura recuperarFaturaPorId(int id) {
        Fatura fatura = faturaDAO.recuperarPorId(id);
        if (fatura == null) {
            throw new EntidadeNaoEncontradaException("Fatura inexistente.");
        }
        return fatura;
    }

    public List<Fatura> recuperarTodasAsFaturasDeUmCliente(int clienteId) {
        return faturaDAO.recuperarTodasAsFaturasDeUmCliente(clienteId);
    }
}