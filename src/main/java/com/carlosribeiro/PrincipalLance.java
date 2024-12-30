package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.exception.ValorDeLanceInvalidoException;
import com.carlosribeiro.model.Lance;
import com.carlosribeiro.model.Produto;
import com.carlosribeiro.service.LanceService;
import com.carlosribeiro.service.ProdutoService;
import corejava.Console;

import java.util.List;

public class PrincipalLance {

    private final LanceService lanceService = new LanceService();
    private final ProdutoService produtoService = new ProdutoService();

    public void principal() {

        double valor;
        Lance umLance;
        Produto umProduto;
        int idProduto, idCliente;

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um lance");
            System.out.println("2. Remover um lance");
            System.out.println("3. Listar todos lances");
            System.out.println("4. Listar todos lances de um produto V1");
            System.out.println("5. Listar todos lances de um produto V2");
            System.out.println("6. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    valor = Console.readDouble("Informe o valor do lance: ");
                    idProduto = Console.readInt("Informe o número do produto: ");
                    try {
                        umProduto = produtoService.recuperarProdutoPorId(idProduto);
                    }
                    catch(EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    umLance = new Lance(valor, umProduto);
                    try {
                        lanceService.incluir(umLance);
                        System.out.println("\nLance número " + umLance.getId() + " cadastrado com sucesso!");
                    } catch (ValorDeLanceInvalidoException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 2 ->    // Remover
                {
                    int id = Console.readInt(
                            "Informe o número do lance que você deseja remover: ");

                    try {
                        lanceService.remover(id);
                        System.out.println('\n' + "Lance removido com sucesso!");
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 3 -> {    // Listar todos os lances
                    List<Lance> lances = lanceService.recuperarLances();
                    for (Lance lance : lances) {
                        System.out.println(lance);
                    }
                }
                case 4 -> {    // Listar todos os lances de um produto
                    int id = Console.readInt("Informe o número do produto: ");
                    try {
                        umProduto = produtoService.recuperarProdutoPorId(id);
                        for (Lance lance : umProduto.getLances()) {
                            System.out.println(lance);
                        }
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 5 -> {    // Listar todos os lances de um produto
                    int id = Console.readInt("Informe o número do produto: ");
                    List<Lance> lances = lanceService.recuperarTodosOsLancesDeUmProduto(id);
                    for (Lance lance : lances) {
                        System.out.println(lance);
                    }
                }
                case 6 -> continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}