package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.ProdutoComLancesException;
import com.carlosribeiro.model.Produto;
import com.carlosribeiro.service.ProdutoService;
import corejava.Console;

import java.util.List;

public class PrincipalProduto {

    private final ProdutoService produtoService = new ProdutoService();

    public void principal() {

        String nome;
        double lanceMinimo;
        Produto umProduto;

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um produto");
            System.out.println("2. Alterar um produto");
            System.out.println("3. Remover um produto");
            System.out.println("4. Listar todos produtos");
            System.out.println("5. Listar todos produtos ordenados por nome");
            System.out.println("6. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    nome = Console.readLine("Informe o nome do produto: ");
                    lanceMinimo = Console.readDouble("Informe o lance mínimo do produto: ");
                    umProduto = new Produto(nome, lanceMinimo);
                    produtoService.incluir(umProduto);
                    System.out.println("\nProduto número " + umProduto.getNumero() + " cadastrado com sucesso!");
                }
                case 2 ->    // Alterar
                {
                    int id = Console.readInt("Informe o número do produto que você deseja alterar: ");

                    try {
                        umProduto = produtoService.recuperarProdutoPorId(id);
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' + "O que você deseja alterar?");
                    System.out.println('\n' + "1. Nome");
                    System.out.println("2. Lance mínimo");

                    int opcaoAlteracao = Console.readInt('\n' + "Digite 1 ou 2:");

                    switch (opcaoAlteracao) {
                        case 1 -> {
                            String novoNome = Console.readLine("Informe o novo nome: ");
                            produtoService.alterar(umProduto, novoNome);
                            System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
                        }
                        case 2 -> {
                            double novoLanceMinimo = Console.readDouble("Informe o novo lance mínimo: ");
                            produtoService.alterar(umProduto, novoLanceMinimo);
                            System.out.println('\n' + "Alteração de lance mínimo efetuado com sucesso!");
                        }
                        default -> System.out.println('\n' + "Opção inválida!");
                    }
                }
                case 3 ->    // Remover
                {
                    int id = Console.readInt("Informe o número do produto que você deseja remover: ");

                    try {
                        produtoService.remover(id);
                        System.out.println('\n' + "Produto removido com sucesso!");
                    } catch (EntidadeNaoEncontradaException | ProdutoComLancesException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 4 -> {    // Listar tudo
                    List<Produto> produtos = produtoService.recuperarProdutos();
                    for (Produto produto : produtos) {
                        System.out.println(produto);
                    }
                }
                case 5 -> {    // Listar tudo
                    List<Produto> produtos = produtoService.recuperarProdutosOrdenadosPorNome();
                    for (Produto produto : produtos) {
                        System.out.println(produto);
                    }
                }
                case 6 ->    // Sair
                    continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}