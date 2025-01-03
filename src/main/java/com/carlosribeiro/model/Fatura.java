package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.time.LocalDate;

public class Fatura implements Serializable {
    @Id
    private int id;
    private LocalDate dataEmissao;
    private LocalDate dataCancelamento;
    private Cliente cliente;
    private int valorTotalFatura; // New attribute
    private int valorDescontadoFatura; // New attribute

    public Fatura(LocalDate dataEmissao, LocalDate dataCancelamento, Cliente cliente, int valorTotalFatura, int valorDescontadoFatura) {
        this.dataEmissao = dataEmissao;
        this.dataCancelamento = dataCancelamento;
        this.cliente = cliente;
        this.valorTotalFatura = valorTotalFatura;
        this.valorDescontadoFatura = valorDescontadoFatura;
    }

    // Getters and setters for all attributes, including the new ones
    public int getId() {
        return id;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getValorTotalFatura() {
        return valorTotalFatura;
    }

    public int getValorDescontadoFatura() {
        return valorDescontadoFatura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setDataCancelamento(LocalDate dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setValorTotalFatura(int valorTotalFatura) {
        this.valorTotalFatura = valorTotalFatura;
    }

    public void setValorDescontadoFatura(int valorDescontadoFatura) {
        this.valorDescontadoFatura = valorDescontadoFatura;
    }
}