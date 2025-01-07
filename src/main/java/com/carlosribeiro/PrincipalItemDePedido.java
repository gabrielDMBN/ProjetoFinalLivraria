package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.TentativaAcessoIndevidoException;
import com.carlosribeiro.exception.ItemComDependenciasException;
import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.service.ItemDePedidoService;
import corejava.Console;

import java.util.List;

public class PrincipalItemDePedido {

    private final ItemDePedidoService itemDePedidoService = new ItemDePedidoService();
    //private final PedidoService pedidoService = new PedidoService();

    public void principal(Pedido pedido) {
        boolean continua = true;
        boolean modificado = false;

        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Gerenciando itens de pedido para o pedido: " + pedido.getId());
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Adicionar item ao pedido");
            System.out.println("2. Listar itens do pedido");
            System.out.println("3. Remover item do pedido");
            System.out.println("4. Voltar e enviar email");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 4:");

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
                            modificado = true;
                            break;
                        } catch (EntidadeNaoEncontradaException | TentativaAcessoIndevidoException | ItemComDependenciasException e) {
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
                case 3 -> {
                    List<ItemDePedido> itens = itemDePedidoService.recuperarItensDePedidoPorPedido(pedido.getId());
                    if (itens.isEmpty()) {
                        System.out.println("Nenhum item para remover.");
                        break;
                    }
                    for (ItemDePedido item : itens) {
                        System.out.println("ID: " + item.getId() + " | Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
                    }
                    int itemId = Console.readInt("Informe o ID do item que deseja remover: ");
                    try {
                        itemDePedidoService.remover(itemId , pedido.getId());
                        pedido.getItensDePedido().removeIf(item -> item.getId() == itemId);
                        System.out.println("Item removido com sucesso!");
                        modificado = true;
                    } catch (EntidadeNaoEncontradaException | TentativaAcessoIndevidoException | ItemComDependenciasException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    if (pedido.getItensDePedido().isEmpty()) {
                        System.out.println("O pedido está vazio. Não é necessário enviar o email.");
                        continua = false;
                    } else if (modificado == false) {
                        System.out.println("O pedido não foi modificado. Não é necessário enviar o email.");
                        continua = false;
                    }
                    else{
                        PrincipalEnvioEmail envioEmail = new PrincipalEnvioEmail();
                        envioEmail.enviarEmail(pedido.getCliente());
                        continua = false;
                    }
                }

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}