package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;

public class ItemFaturado implements Serializable {
    @Id
    private int id;
    private int qtdFaturada;
    private ItemDePedido itemDePedido;
    private Fatura fatura;

    public ItemFaturado(int qtdFaturada, ItemDePedido itemDePedido, Fatura fatura) {
        this.qtdFaturada = qtdFaturada;
        this.itemDePedido = itemDePedido;
        this.fatura = fatura;

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

    public ItemDePedido getItemDePedido() {
        return itemDePedido;
    }

    public void setItemDePedido(ItemDePedido itemDePedido) {
        this.itemDePedido = itemDePedido;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }
}