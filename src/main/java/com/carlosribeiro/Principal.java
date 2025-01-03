package com.carlosribeiro;

import com.carlosribeiro.dao.*;
import com.carlosribeiro.model.*;
import com.carlosribeiro.util.FabricaDeDaos;
import corejava.Console;

import java.io.*;
import java.util.Map;

public class Principal {
    public static void main(String[] args) {

        PrincipalProduto principalProduto = new PrincipalProduto();
        PrincipalLance principalLance = new PrincipalLance();
        PrincipalCliente principalCliente = new PrincipalCliente();
        PrincipalLivro principalLivro = new PrincipalLivro();
        PrincipalPedido principalPedido = new PrincipalPedido();

        recuperarDados();

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Tratar Produtos");
            System.out.println("2. Tratar Lances");
            System.out.println("3. Sair");
            System.out.println("4. Tratar Clientes"); //aaaaaaaaaaaaaaaaaaaaa
            System.out.println("5. Tratar Livros"); //aaaaaaaaaaaaaaaaaaaaa


            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 3:");

            System.out.println();

            switch (opcao) {
                case 1 -> {
                    principalProduto.principal();
                }
                case 2 -> {
                    principalLance.principal();
                }
                case 3 -> {
                    salvarDados();
                    continua = false;
                }
                case 4 -> {
                    principalCliente.principal();
                }
                case 5 -> {
                    principalLivro.principal();
                }
                case 6 -> {
                //    principalPedido.principal();
                }

                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
    //AJUSTAR=====================================================================
    private static void salvarDados() {
        ProdutoDAO produtoDAO = FabricaDeDaos.getDAO(ProdutoDAO.class);
        LanceDAO lanceDAO = FabricaDeDaos.getDAO(LanceDAO.class);
        ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
        PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);
        LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);

        Map<Integer, Produto> mapDeProdutos = produtoDAO.getMap();
        int contadorProdutos = produtoDAO.getContador();
        Map<Integer, Lance> mapDeLances = lanceDAO.getMap();
        int contadorLances = lanceDAO.getContador();
        Map<Integer, Cliente> mapDeClientes = clienteDAO.getMap();
        int contadorClientes = clienteDAO.getContador();
        Map<Integer, Pedido> mapDePedidos = pedidoDAO.getMap();
        int contadorPedidos = pedidoDAO.getContador();
        Map<Integer, Livro> mapDeLivros = livroDAO.getMap();
        int contadorLivros = livroDAO.getContador();

        try {
            FileOutputStream fos = new FileOutputStream("arquivo.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mapDeProdutos);
            oos.writeInt(contadorProdutos);
            oos.writeObject(mapDeLances);
            oos.writeInt(contadorLances);
            oos.writeObject(mapDeClientes);
            oos.writeInt(contadorClientes);
            oos.writeObject(mapDePedidos);
            oos.writeInt(contadorPedidos);
            oos.writeObject(mapDeLivros);
            oos.writeInt(contadorLivros);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void recuperarDados() {
        try {
            FileInputStream fis = new FileInputStream("arquivo.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Map<Integer, Produto> mapDeProdutos = (Map<Integer, Produto>) ois.readObject();
            int contadorProdutos = ois.readInt();
            Map<Integer, Lance> mapDeLances = (Map<Integer, Lance>) ois.readObject();
            int contadorLances = ois.readInt();
            Map<Integer, Cliente> mapDeClientes = (Map<Integer, Cliente>) ois.readObject();
            int contadorClientes = ois.readInt();
            Map<Integer, Pedido> mapDePedidos = (Map<Integer, Pedido>) ois.readObject();
            int contadorPedidos = ois.readInt();
            Map<Integer, Livro> mapDeLivros = (Map<Integer, Livro>) ois.readObject();
            int contadorLivros = ois.readInt();

            ProdutoDAO produtoDAO = FabricaDeDaos.getDAO(ProdutoDAO.class);
            LanceDAO lanceDAO = FabricaDeDaos.getDAO(LanceDAO.class);
            ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
            PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);
            LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);

            produtoDAO.setMap(mapDeProdutos);
            produtoDAO.setContador(contadorProdutos);
            lanceDAO.setMap(mapDeLances);
            lanceDAO.setContador(contadorLances);
            clienteDAO.setMap(mapDeClientes);
            clienteDAO.setContador(contadorClientes);
            pedidoDAO.setMap(mapDePedidos);
            pedidoDAO.setContador(contadorPedidos);
            livroDAO.setMap(mapDeLivros);
            livroDAO.setContador(contadorLivros);

            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("o arquivo não existe e será criado.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}