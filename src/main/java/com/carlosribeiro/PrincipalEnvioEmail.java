package com.carlosribeiro;

import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.util.Tarefa;

public class PrincipalEnvioEmail {
    public void enviarEmail(Cliente cliente) {
        Tarefa tarefa = new Tarefa(cliente);
        tarefa.start();
        try {
            tarefa.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}