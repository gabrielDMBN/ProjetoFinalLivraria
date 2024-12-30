package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.time.LocalDate;

public class Pedido implements Serializable {
    @Id
    private int id;
    private LocalDate dataEmissao;
    private String dataCancelamento;
    private String status;
    private Cliente cliente;

    public Pedido(LocalDate dataEmissao, String dataCancelamento, String status, Cliente cliente) {
        this.dataEmissao = dataEmissao;
        this.dataCancelamento = dataCancelamento;
        this.status = status;
        this.cliente = cliente;
    }

    public String toString() {
        return "ID = " + id +
                "  Data de Emissão = " + dataEmissao +
                "  Data de Cancelamento = " + dataCancelamento +
                "  Status = " + status;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public String getDataCancelamento() {
        return dataCancelamento;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setDataCancelamento(String dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}