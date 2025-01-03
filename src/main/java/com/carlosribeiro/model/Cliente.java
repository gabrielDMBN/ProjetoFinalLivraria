package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    @Id
    private int id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private List<Pedido> pedidos;
    private List<Fatura> faturas;

    public Cliente(String cpf, String nome, String email, String telefone, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.pedidos = new ArrayList<>();
        this.faturas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ID = " + id +
                "  CPF = " + cpf +
                "  Nome = " + nome +
                "  Email = " + email +
                "  Telefone = " + telefone +
                "  Endereço = " + endereco ; ////////+ "  N Pedidos = " + pedidos.size()//////////////////
    }

    // Getters and setters for all attributes, including the new one
    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }
}