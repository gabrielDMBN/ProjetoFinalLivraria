package com.carlosribeiro;

import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.service.ClienteService;
import corejava.Console;

import java.util.List;

public class PrincipalCliente {

    private final ClienteService clienteService = new ClienteService();
    private final PrincipalPedido principalPedido = new PrincipalPedido();

    public void principal() {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um cliente");
            System.out.println("2. Alterar um cliente");
            System.out.println("3. Remover um cliente");
            System.out.println("4. Listar todos clientes");
            System.out.println("5. Gerenciar pedidos de um cliente");
            System.out.println("6. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    String cpf = Console.readLine("Informe o CPF do cliente: ");
                    String nome = Console.readLine("Informe o nome do cliente: ");
                    String email = Console.readLine("Informe o email do cliente: ");
                    String telefone = Console.readLine("Informe o telefone do cliente: ");
                    String endereco = Console.readLine("Informe o endereço do cliente: ");
                    Cliente umCliente = new Cliente(cpf, nome, email, telefone, endereco);
                    clienteService.incluir(umCliente);
                    System.out.println("\nCliente número " + umCliente.getId() + " cadastrado com sucesso!");
                }
                case 2 -> {
                    int id = Console.readInt("Informe o número do cliente que você deseja alterar: ");

                    try {
                        Cliente umCliente = clienteService.recuperarClientePorId(id);
                        System.out.println("Cliente atual: " + umCliente);

                        System.out.println("O que você deseja alterar?");
                        System.out.println("1. CPF");
                        System.out.println("2. Nome");
                        System.out.println("3. Email");
                        System.out.println("4. Telefone");
                        System.out.println("5. Endereço");
                        int opcaoAlterar = Console.readInt("Digite um número entre 1 e 5:");

                        switch (opcaoAlterar) {
                            case 1 -> {
                                String novoCpf = Console.readLine("Informe o novo CPF: ");
                                clienteService.alterarCpf(id, novoCpf);
                                System.out.println('\n' + "Alteração de CPF efetuada com sucesso!");
                            }
                            case 2 -> {
                                String novoNome = Console.readLine("Informe o novo nome: ");
                                clienteService.alterarNome(id, novoNome);
                                System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
                            }
                            case 3 -> {
                                String novoEmail = Console.readLine("Informe o novo email: ");
                                clienteService.alterarEmail(id, novoEmail);
                                System.out.println('\n' + "Alteração de email efetuada com sucesso!");
                            }
                            case 4 -> {
                                String novoTelefone = Console.readLine("Informe o novo telefone: ");
                                clienteService.alterarTelefone(id, novoTelefone);
                                System.out.println('\n' + "Alteração de telefone efetuada com sucesso!");
                            }
                            case 5 -> {
                                String novoEndereco = Console.readLine("Informe o novo endereço: ");
                                clienteService.alterarEndereco(id, novoEndereco);
                                System.out.println('\n' + "Alteração de endereço efetuada com sucesso!");
                            }
                            default -> System.out.println('\n' + "Opção inválida!");
                        }
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 3 -> {
                    int id = Console.readInt("Informe o número do cliente que você deseja remover: ");

                    try {
                        clienteService.remover(id);
                        System.out.println('\n' + "Cliente removido com sucesso!");
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 4 -> {
                    List<Cliente> clientes = clienteService.recuperarClientes();
                    for (Cliente cliente : clientes) {
                        System.out.println(cliente);
                    }
                }
                case 5 -> {
                    int id = Console.readInt("Informe o número do cliente que você deseja gerenciar pedidos: ");
                    try {
                        Cliente cliente = clienteService.recuperarClientePorId(id);
                        principalPedido.principal(cliente);
                    } catch (EntidadeNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 6 -> continua = false;

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}