package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fatura implements Serializable {
    @Id
    private int id;
    private LocalDate dataEmissao;
    private LocalDate dataCancelamento;
    private Cliente cliente;
    private double valorTotalFatura; // New attribute
    private double valorDescontadoFatura; // New attribute
    private List<ItemFaturado> itensFaturados;

    public Fatura(LocalDate dataEmissao, LocalDate dataCancelamento, Cliente cliente, double valorTotalFatura, double valorDescontadoFatura) {
        this.dataEmissao = dataEmissao;
        this.dataCancelamento = dataCancelamento;
        this.cliente = cliente;
        this.valorTotalFatura = valorTotalFatura;
        this.valorDescontadoFatura = valorDescontadoFatura;
        this.itensFaturados = new ArrayList<>();
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

    public double getValorTotalFatura() {
        return valorTotalFatura;
    }

    public double getValorDescontadoFatura() {
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

    public void setValorTotalFatura(double valorTotalFatura) {
        this.valorTotalFatura = valorTotalFatura;
    }

    public void setValorDescontadoFatura(double valorDescontadoFatura) {
        this.valorDescontadoFatura = valorDescontadoFatura;
    }

    public List<ItemFaturado> getItensFaturados() {
        return itensFaturados;
    }

    public void setItensFaturados(List<ItemFaturado> itensFaturados) {
        this.itensFaturados = itensFaturados;
    }
}