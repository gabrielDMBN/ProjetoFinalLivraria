package com.carlosribeiro;

import com.carlosribeiro.service.RelatorioService;
import corejava.Console;

public class PrincipalRelatorio {
    private final RelatorioService relatorioService;

    public PrincipalRelatorio(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public void principal() {
        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Relatórios disponíveis:");
            System.out.println('\n' + "1. Itens faturados por livro e mês");
            System.out.println("2. Livros nunca faturados");
            System.out.println("3. Livros faturados por mês e ano");
            System.out.println("4. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 4:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    int livroId = Console.readInt("Informe o ID do livro: ");
                    int mes = Console.readInt("Informe o mês: ");
                    int ano = Console.readInt("Informe o ano: ");
                    var itens = relatorioService.getItensFaturadosPorLivroEMes(livroId, mes, ano);
                   // itens.forEach(item -> System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdFaturada() + " | Data: " + item.getDataFatura()));
                }
                case 2 -> {
                    var livros = relatorioService.getLivrosNuncaFaturados();
                    System.out.println("Livros nunca faturados:");
                    livros.forEach(livro -> System.out.println("- " + livro.getTitulo()));
                }
                case 3 -> {
                    int mes = Console.readInt("Informe o mês: ");
                    int ano = Console.readInt("Informe o ano: ");
                    var itens = relatorioService.getLivrosFaturadosPorMesEAno(mes, ano);
                   // itens.forEach(item -> System.out.println("Produto: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdFaturada()));
                }
                case 4 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}