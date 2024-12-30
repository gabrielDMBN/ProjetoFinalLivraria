package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.service.PedidoService;
import corejava.Console;

import java.time.LocalDate;
import java.util.List;

public class PrincipalPedido {

    private final PedidoService pedidoService = new PedidoService();

    public void principal(Cliente cliente) {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Gerenciando pedidos para o cliente: " + cliente.getNome());
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um pedido");
            System.out.println("2. Cancelar um pedido");
            System.out.println("3. Listar todos pedidos");
            System.out.println("4. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 4:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    LocalDate dataEmissao = LocalDate.now();
                    Pedido umPedido = new Pedido(dataEmissao, null, "Processando", cliente);
                    pedidoService.incluir(umPedido);
                    System.out.println("\nPedido número " + umPedido.getId() + " cadastrado com sucesso!");
                }
                case 2 -> {
                    int id = Console.readInt("Informe o número do pedido que você deseja cancelar (ou 0 para voltar): ");
                    if (id == 0) {
                        continue;
                    }
                    try {
                        pedidoService.cancelarPedido(id, cliente.getId());
                        System.out.println('\n' + "Pedido cancelado com sucesso!");
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 3 -> {
                    List<Pedido> pedidos = pedidoService.recuperarTodosOsPedidosDeUmCliente(cliente.getId());
                    for (Pedido pedido : pedidos) {
                        System.out.println(pedido);
                    }
                }
                case 4 -> continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}