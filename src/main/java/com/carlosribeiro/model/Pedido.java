package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    @Id
    private int id;
    private LocalDate dataEmissao;
    private String dataCancelamento;
    private String status;
    private Cliente cliente;
    private String enderecoEntrega;
    private List<ItemDePedido> itensDePedido;

    public Pedido(LocalDate dataEmissao, String dataCancelamento, String status, Cliente cliente, String enderecoEntrega) {
        this.dataEmissao = dataEmissao;
        this.dataCancelamento = dataCancelamento;
        this.status = status;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
        this.itensDePedido = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ID = " + id +
                "  Data de Emissão = " + dataEmissao +
                "  Data de Cancelamento = " + dataCancelamento +
                "  Status = " + status +
                "  Endereço de Entrega = " + enderecoEntrega;
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

    public Cliente getCliente() {
        return cliente;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public List<ItemDePedido> getItensDePedido() {
        return itensDePedido;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public void setItensDePedido(List<ItemDePedido> itensDePedido) {
        this.itensDePedido = itensDePedido;
    }
}