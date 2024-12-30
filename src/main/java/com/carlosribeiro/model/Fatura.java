package com.carlosribeiro.model;

import com.carlosribeiro.util.Id;

import java.io.Serializable;
import java.time.LocalDate;

public class Fatura implements Serializable {
    @Id
    private int id;
    private LocalDate dataEmissao;
    private LocalDate dataCancelamento;

    public Fatura(LocalDate dataEmissao, LocalDate dataCancelamento) {
        this.dataEmissao = dataEmissao;
        this.dataCancelamento = dataCancelamento;
    }

    public String toString() {
        return "ID = " + id +
                "  Data de Emissão = " + dataEmissao +
                "  Data de Cancelamento = " + dataCancelamento;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
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
}