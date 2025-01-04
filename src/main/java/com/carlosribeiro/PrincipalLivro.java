package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.service.LivroService;
import corejava.Console;

import java.util.List;

public class PrincipalLivro {

    private final LivroService livroService = new LivroService();

    public void principal() {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um livro");
            System.out.println("2. Alterar um livro");
            System.out.println("3. Remover um livro");
            System.out.println("4. Listar todos livros");
            System.out.println("5. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    String isbn = Console.readLine("Informe o ISBN do livro: ");
                    String titulo = Console.readLine("Informe o título do livro: ");
                    String descricao = Console.readLine("Informe a descrição do livro: ");
                    int qtdEstoque = Console.readInt("Informe a quantidade em estoque do livro: ");
                    double preco = Console.readDouble("Informe o preço do livro: ");
                    Livro umLivro = new Livro(isbn, titulo, descricao, qtdEstoque, preco);
                    livroService.incluir(umLivro);
                    System.out.println("\nLivro número " + umLivro.getId() + " cadastrado com sucesso!");
                }
                case 2 -> {
                    int id = Console.readInt("Informe o número do livro que você deseja alterar: ");

                    try {
                        Livro umLivro = livroService.recuperarLivroPorId(id);
                        System.out.println("Livro atual: " + umLivro);

                        System.out.println("O que você deseja alterar?");
                        System.out.println("1. ISBN");
                        System.out.println("2. Título");
                        System.out.println("3. Descrição");
                        System.out.println("4. Quantidade em Estoque");
                        System.out.println("5. Preço");
                        int opcaoAlterar = Console.readInt("Digite um número entre 1 e 5:");

                        switch (opcaoAlterar) {
                            case 1 -> {
                                String novoIsbn = Console.readLine("Informe o novo ISBN: ");
                                livroService.alterarIsbn(id, novoIsbn);
                                System.out.println('\n' + "Alteração de ISBN efetuada com sucesso!");
                            }
                            case 2 -> {
                                String novoTitulo = Console.readLine("Informe o novo título: ");
                                livroService.alterarTitulo(id, novoTitulo);
                                System.out.println('\n' + "Alteração de título efetuada com sucesso!");
                            }
                            case 3 -> {
                                String novaDescricao = Console.readLine("Informe a nova descrição: ");
                                livroService.alterarDescricao(id, novaDescricao);
                                System.out.println('\n' + "Alteração de descrição efetuada com sucesso!");
                            }
                            case 4 -> {
                                int novaQtdEstoque = Console.readInt("Informe a nova quantidade em estoque: ");
                                livroService.alterarQtdEstoque(id, novaQtdEstoque);
                                System.out.println('\n' + "Alteração de quantidade em estoque efetuada com sucesso!");
                            }
                            case 5 -> {
                                double novoPreco = Console.readDouble("Informe o novo preço: ");
                                livroService.alterarPreco(id, novoPreco);
                                System.out.println('\n' + "Alteração de preço efetuada com sucesso!");
                            }
                            default -> System.out.println('\n' + "Opção inválida!");
                        }
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 3 -> {
                    int id = Console.readInt("Informe o número do livro que você deseja remover: ");

                    try {
                        livroService.remover(id);
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 4 -> {
                    List<Livro> livros = livroService.recuperarLivros();
                    for (Livro livro : livros) {
                        System.out.println(livro);
                    }
                }
                case 5 -> continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}