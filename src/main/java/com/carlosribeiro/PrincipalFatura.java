package com.carlosribeiro;

import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Fatura;
import com.carlosribeiro.model.ItemFaturado;
import com.carlosribeiro.service.FaturaService;
import corejava.Console;

import java.util.List;
import java.util.Scanner;

public class PrincipalFatura {

    private static final FaturaService faturaService = new FaturaService();


    public static void principal(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Ver faturas do cliente");
            System.out.println("2. Cancelar uma fatura");
            System.out.println("3. Remover uma fatura");
            System.out.println("4. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 4:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    List<Fatura> faturas = faturaService.recuperarTodasAsFaturasDeUmCliente(cliente.getId());

                    if (faturas.isEmpty()) {
                        System.out.println("Nenhuma fatura encontrada para o cliente ID: " + cliente.getId());
                    } else {
                        System.out.println("Faturas do cliente ID: " + cliente.getId());
                        for (Fatura fatura : faturas) {
                            System.out.println("-----------------------------");
                            System.out.println("Fatura ID: " + fatura.getId());
                           // System.out.println("Pedido ID: " + fatura.getPedido().getId());
                            System.out.println("Data de Emissão: " + fatura.getDataEmissao());
                            System.out.println("Data de Cancelamento: " + fatura.getDataCancelamento());
                            System.out.println("Valor Total: " + fatura.getValorTotalFatura());
                            System.out.println("Valor Descontado: " + fatura.getValorDescontadoFatura());
                            System.out.println("Itens Faturados: " + fatura.getItensFaturados().size());
                            for (ItemFaturado item : fatura.getItensFaturados()) {
                                System.out.println("  - Livro: " + item.getItemDePedido().getLivro().getTitulo());
                                System.out.println("    Preço: " + item.getItemDePedido().getLivro().getPreco());
                                System.out.println("    Quantidade: " + item.getQtdFaturada());
                            }
                            System.out.println("-----------------------------");
                        }
                    }
                }
                case 2 -> {
                    int faturaId = Console.readInt("Informe o ID da fatura que deseja cancelar: ");
                    try {
                        faturaService.cancelarFatura(faturaId, cliente.getId());
                        System.out.println("Fatura cancelada com sucesso!");
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println("Erro ao cancelar a fatura: " + e.getMessage());
                    }
                }
                case 3 -> {
                    int faturaId = Console.readInt("Informe o ID da fatura que deseja remover: ");
                    try {
                        faturaService.remover(faturaId, cliente.getId());
                       // System.out.println("Fatura removida com sucesso!");
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println("Erro ao remover a fatura: " + e.getMessage());
                    }
                }
                case 4 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}