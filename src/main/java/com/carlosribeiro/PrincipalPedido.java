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
    private final PrincipalItemDePedido principalItemDePedido = new PrincipalItemDePedido();
    private final PrincipalItemFaturado principalItemFaturado = new PrincipalItemFaturado();

    public void principal(Cliente cliente) {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Gerenciando pedidos para o cliente: " + cliente.getNome());
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um pedido");
            System.out.println("2. Cancelar um pedido");
            System.out.println("3. Listar todos pedidos");
            System.out.println("4. Gerenciar itens de um pedido");
            System.out.println("5. Faturar Pedido");
            System.out.println("6. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    LocalDate dataEmissao = LocalDate.now();
                    String enderecoEntrega = Console.readLine("Informe o endereço de entrega: ");
                    Pedido umPedido = new Pedido(dataEmissao, null, "Processando", cliente, enderecoEntrega);
                    pedidoService.incluir(umPedido);
                    principalItemDePedido.principal(umPedido);
                    if (umPedido.getItensDePedido().isEmpty()) {
                        pedidoService.remover(umPedido.getId());
                        System.out.println("\nPedido removido pois não possui itens.");
                    } else {
                        System.out.println("\nPedido número " + umPedido.getId() + " cadastrado com sucesso!");
                    }
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
                case 4 -> {
                    while (true) {
                        int pedidoId = Console.readInt("Informe o número do pedido que você deseja ver os itens (ou 0 para voltar): ");
                        if (pedidoId == 0) {
                            break;
                        }
                        try {
                            Pedido pedido = pedidoService.recuperarPedidoPorId(pedidoId);
                            if (pedido.getCliente().getId() != cliente.getId()) {
                                System.out.println("Este pedido não pertence a este cliente.");
                                continue;
                            }
                            principalItemDePedido.principal(pedido);
                            if (pedido.getItensDePedido().isEmpty()) {
                                pedidoService.remover(pedido.getId());
                                System.out.println("Pedido removido pois não possui itens.");
                            }
                        } catch (EntidadeNaoEncontradaException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case 5 -> principalItemFaturado.principal(cliente.getId());
                case 6 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}