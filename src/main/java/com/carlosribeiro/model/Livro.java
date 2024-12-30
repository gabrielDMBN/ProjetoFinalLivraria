package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Livro implements Serializable {
    @Id
    private int id;
    private String isbn;
    private String titulo;
    private String descricao;
    private int qtdEstoque;
    private double preco;
    private List<ItemDePedido> itemDePedidos;

    public Livro(String isbn, String titulo, String descricao, int qtdEstoque, double preco) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.descricao = descricao;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.itemDePedidos = new ArrayList<>();
    }

    public String toString() {
        return "ID = " + id +
                "  ISBN = " + isbn +
                "  Título = " + titulo +
                "  Descrição = " + descricao +
                "  Quantidade em Estoque = " + qtdEstoque +
                "  Preço = " + preco;
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public List<ItemDePedido> getItemDePedidos() {
        return itemDePedidos;
    }

    public void setItemDePedidos(List<ItemDePedido> itemDePedidos) {
        this.itemDePedidos = itemDePedidos;
    }
}