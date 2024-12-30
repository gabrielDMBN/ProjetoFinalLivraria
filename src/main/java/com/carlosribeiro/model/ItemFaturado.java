package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;

public class ItemFaturado implements Serializable {
    @Id
    private int id;
    private int qtdFaturada;

    public ItemFaturado(int qtdFaturada) {
        this.qtdFaturada = qtdFaturada;
    }

    public String toString() {
        return "ID = " + id +
                "  Quantidade Faturada = " + qtdFaturada;
    }

    public int getId() {
        return id;
    }

    public int getQtdFaturada() {
        return qtdFaturada;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQtdFaturada(int qtdFaturada) {
        this.qtdFaturada = qtdFaturada;
    }
}