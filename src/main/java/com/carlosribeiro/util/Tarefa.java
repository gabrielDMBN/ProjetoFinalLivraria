package com.carlosribeiro.util;

import com.carlosribeiro.model.Cliente;

public class Tarefa extends Thread {
    private Cliente cliente;

    public Tarefa(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
                Thread.sleep(2000); // sending delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Enviando email para cliente " + cliente.getEmail() + "...");
    }
}