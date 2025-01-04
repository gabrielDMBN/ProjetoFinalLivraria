package com.carlosribeiro;

import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.service.ItemDePedidoService;
import com.carlosribeiro.service.ItemFaturadoService;
import com.carlosribeiro.service.PedidoService;

import java.util.Scanner;

public class PrincipalItemFaturado {

    private static final PedidoService pedidoService = new PedidoService();
    private static final ItemFaturadoService itemFaturadoService = new ItemFaturadoService();
    private static final ItemDePedidoService itemDePedidoService = new ItemDePedidoService();

    public void principal(int clienteId) {
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;
        while (continua) {
            itemDePedidoService.listarPedidosResumidos(clienteId);

            System.out.println("Escolha uma opção:");
            System.out.println("1. Faturar Pedido");
            System.out.println("2. Voltar");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.println("Digite o ID do Pedido:");
                    int pedidoId = scanner.nextInt();
                    Pedido pedido = pedidoService.recuperarPedidoPorId(pedidoId);

                    if (pedido != null && pedido.getCliente().getId() == clienteId) {
                        boolean algumItemFaturado = false;
                        for (ItemDePedido itemDePedido : pedido.getItensDePedido()) {
                            algumItemFaturado |= itemFaturadoService.faturarPedido(itemDePedido);
                        }
                        if (algumItemFaturado) {
                            System.out.println("Pedido faturado com sucesso.");
                        }
//                        else {
//                            System.out.println("Nenhum item foi faturado devido à falta de estoque.");
//                        }
                    } else {
                        System.out.println("Pedido não encontrado ou não pertence ao cliente.");
                    }
                }
                case 2 -> continua = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}