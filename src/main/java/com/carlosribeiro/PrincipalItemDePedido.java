package com.carlosribeiro;

import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.service.ItemDePedidoService;
import corejava.Console;

import java.util.List;

public class PrincipalItemDePedido {

    private final ItemDePedidoService itemDePedidoService = new ItemDePedidoService();

    public void principal(Pedido pedido) {
        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Gerenciando itens de pedido para o pedido: " + pedido.getId());
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Adicionar item ao pedido");
            System.out.println("2. Listar itens do pedido");
            System.out.println("3. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 3:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    List<Livro> livros = itemDePedidoService.listarLivros();
                    System.out.println("Livros disponíveis:");
                    for (Livro livro : livros) {
                        System.out.println(livro);
                    }
                    while (true) {
                        int livroId = Console.readInt("Informe o ID do livro que deseja adicionar ao pedido (ou 0 para voltar): ");
                        if (livroId == 0) {
                            break;
                        }
                        Livro livroSelecionado = livros.stream()
                                .filter(livro -> livro.getId() == livroId)
                                .findFirst()
                                .orElse(null);
                        if (livroSelecionado == null) {
                            System.out.println("Livro não encontrado. Tente novamente.");
                            continue;
                        }
                        int quantidade = Console.readInt("Informe a quantidade desejada: ");
                        try {
                            ItemDePedido itemDePedido = itemDePedidoService.incluirItemDePedido(pedido, livroSelecionado, quantidade);
                            pedido.getItensDePedido().add(itemDePedido);
                            System.out.println("Item adicionado ao pedido com sucesso!");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case 2 -> {
                    List<ItemDePedido> itens = itemDePedidoService.recuperarItensDePedidoPorPedido(pedido.getId());
                    for (ItemDePedido item : itens) {
                        double valorTotal = item.getLivro().getPreco() * item.getQtdPedida();
                        System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida() + " | Quantidade a Faturar: " + item.getQtdAFaturar() + " | Valor Total: " + valorTotal);
                    }
                }
                case 3 -> continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}