package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDePedido implements Serializable {
    @Id
    private int id;
    private int qtdPedida;
    private int qtdRestante;
    private int qtdAFaturar;
    private double precoCobrado;
    private Pedido pedido;
    private  Livro livro;
    private List<ItemFaturado> itensFaturados;

    public ItemDePedido(int qtdPedida, int qtdRestante, int qtdAFaturar, double precoCobrado, Pedido pedido, Livro livro) {
        this.qtdPedida = qtdPedida;
        this.qtdRestante = qtdRestante;
        this.qtdAFaturar = qtdAFaturar;
        this.precoCobrado = precoCobrado;
        this.pedido = pedido;
        this.livro = livro;
        this.itensFaturados = new ArrayList<>();
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<ItemFaturado> getItensFaturados() {
        return itensFaturados;
    }

    public void setItensFaturados(List<ItemFaturado> itensFaturados) {
        this.itensFaturados = itensFaturados;
    }
}