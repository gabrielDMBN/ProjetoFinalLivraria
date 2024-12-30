package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Lance implements Serializable {
    @Id
    private int id;
    private double valor;
    private Produto produto;

    private static final NumberFormat NF;

    static {
        NF = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        NF.setMaximumFractionDigits(2);       // O default é 3.
        NF.setMinimumFractionDigits(2);
    }

    public Lance(double valor, Produto produto) {
        this.valor = valor;
        this.produto = produto;
    }

    public String toString() {
        return "Número = " + id +
                "   Valor = " + getValorMasc() +
                "   Produto = " + getProduto().getNome() +
                "   Lance mínimo = " + getProduto().getLanceMinimoMasc() +
                "   Valor do último lance = " + getProduto().getValorUltimoLanceMasc();
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public String getValorMasc() {
        return NF.format(valor);
    }

    public Produto getProduto() {
        return produto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}