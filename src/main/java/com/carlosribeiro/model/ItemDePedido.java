package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;

public class ItemDePedido implements Serializable {
    @Id
    private int id;
    private int qtdPedida;
    private int qtdRestante;
    private int qtdAFaturar;
    private double precoCobrado;

    public ItemDePedido(int qtdPedida, int qtdRestante, int qtdAFaturar, double precoCobrado) {
        this.qtdPedida = qtdPedida;
        this.qtdRestante = qtdRestante;
        this.qtdAFaturar = qtdAFaturar;
        this.precoCobrado = precoCobrado;
    }

    public String toString() {
        return "ID = " + id +
                "  Quantidade Pedida = " + qtdPedida +
                "  Quantidade Restante = " + qtdRestante +
                "  Quantidade a Faturar = " + qtdAFaturar +
                "  Preço Cobrado = " + precoCobrado;
    }

    public int getId() {
        return id;
    }

    public int getQtdPedida() {
        return qtdPedida;
    }

    public int getQtdRestante() {
        return qtdRestante;
    }

    public int getQtdAFaturar() {
        return qtdAFaturar;
    }

    public double getPrecoCobrado() {
        return precoCobrado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQtdPedida(int qtdPedida) {
        this.qtdPedida = qtdPedida;
    }

    public void setQtdRestante(int qtdRestante) {
        this.qtdRestante = qtdRestante;
    }

    public void setQtdAFaturar(int qtdAFaturar) {
        this.qtdAFaturar = qtdAFaturar;
    }

    public void setPrecoCobrado(double precoCobrado) {
        this.precoCobrado = precoCobrado;
    }
}