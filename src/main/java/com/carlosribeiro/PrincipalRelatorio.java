package com.carlosribeiro;

import com.carlosribeiro.model.Livro;
import com.carlosribeiro.service.LivroService;
import com.carlosribeiro.service.RelatorioService;
import com.carlosribeiro.service.ItemFaturadoService;
import corejava.Console;

import java.util.List;

public class PrincipalRelatorio {

    private final RelatorioService relatorioService;
    private final ItemFaturadoService itemFaturadoService = new ItemFaturadoService();
    private final LivroService livroService = new LivroService();

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
                    List<Livro> livros = livroService.recuperarLivros();
                    System.out.println("Livros cadastrados:");
                    for (Livro livro : livros) {
                        System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo() + ", Descrição: " + livro.getDescricao());
                    }
                    int livroId = Console.readInt("Informe o ID do livro: ");
                    int mes = Console.readInt("Informe o mês: ");
                    int ano = Console.readInt("Informe o ano: ");
                    var itens = relatorioService.getItensFaturadosPorLivroEMes(livroId, mes, ano);
                    System.out.println("Itens faturados no mês " + mes + " do ano " + ano + ":");
                    if (itens.isEmpty()) {
                        System.out.println("Nenhum item faturado encontrado.");
                    } else {
                        itens.forEach(item -> System.out.println("- " + itemFaturadoService.relatorioUmResumo(item)));
                    }
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
                    System.out.println("Livros faturados no mês " + mes + " do ano " + ano + ":");
                    if (itens.isEmpty()) {
                        System.out.println("Nenhum livro faturado encontrado.");
                    } else {
                        relatorioService.consolidarItensPorNome(itens);
                    }
                }
                case 4 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}