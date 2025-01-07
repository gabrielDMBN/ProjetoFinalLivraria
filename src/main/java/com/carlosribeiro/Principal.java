package com.carlosribeiro;

import com.carlosribeiro.dao.*;
import com.carlosribeiro.model.*;
import com.carlosribeiro.service.RelatorioService;
import com.carlosribeiro.util.FabricaDeDaos;
import corejava.Console;

import java.io.*;
import java.util.Map;

public class Principal {
    public static void main(String[] args) {
        PrincipalCliente principalCliente = new PrincipalCliente();
        PrincipalLivro principalLivro = new PrincipalLivro();
        PrincipalRelatorio principalRelatorio = new PrincipalRelatorio(new RelatorioService());

       recuperarDados();

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Tratar Clientes");
            System.out.println("2. Tratar Livros");
            System.out.println("3. Tratar Relatórios");
            System.out.println("4. Sair");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 4:");

            System.out.println();

            switch (opcao) {
                case 1 -> principalCliente.principal();
                case 2 -> principalLivro.principal();
                case 3 -> principalRelatorio.principal();
                case 4 -> {
                    salvarDados();
                    continua = false;
                }
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }

    private static void salvarDados() {
        ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
        PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);
        LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);
        ItemDePedidoDAO itemDePedidoDAO = FabricaDeDaos.getDAO(ItemDePedidoDAO.class);
        ItemFaturadoDAO itemFaturadoDAO = FabricaDeDaos.getDAO(ItemFaturadoDAO.class);
        FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);

        Map<Integer, Cliente> mapDeClientes = clienteDAO.getMap();
        int contadorClientes = clienteDAO.getContador();
        Map<Integer, Pedido> mapDePedidos = pedidoDAO.getMap();
        int contadorPedidos = pedidoDAO.getContador();
        Map<Integer, Livro> mapDeLivros = livroDAO.getMap();
        int contadorLivros = livroDAO.getContador();
        Map<Integer, ItemDePedido> mapDeItensDePedido = itemDePedidoDAO.getMap();
        int contadorItensDePedido = itemDePedidoDAO.getContador();
        Map<Integer, ItemFaturado> mapDeItensFaturados = itemFaturadoDAO.getMap();
        int contadorItensFaturados = itemFaturadoDAO.getContador();
        Map<Integer, Fatura> mapDeFaturas = faturaDAO.getMap();
        int contadorFaturas = faturaDAO.getContador();

        try {
            FileOutputStream fos = new FileOutputStream("arquivo.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mapDeClientes);
            oos.writeInt(contadorClientes);
            oos.writeObject(mapDePedidos);
            oos.writeInt(contadorPedidos);
            oos.writeObject(mapDeLivros);
            oos.writeInt(contadorLivros);
            oos.writeObject(mapDeItensDePedido);
            oos.writeInt(contadorItensDePedido);
            oos.writeObject(mapDeItensFaturados);
            oos.writeInt(contadorItensFaturados);
            oos.writeObject(mapDeFaturas);
            oos.writeInt(contadorFaturas);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void recuperarDados() {
        try {
            FileInputStream fis = new FileInputStream("arquivo.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Map<Integer, Cliente> mapDeClientes = (Map<Integer, Cliente>) ois.readObject();
            int contadorClientes = ois.readInt();
            Map<Integer, Pedido> mapDePedidos = (Map<Integer, Pedido>) ois.readObject();
            int contadorPedidos = ois.readInt();
            Map<Integer, Livro> mapDeLivros = (Map<Integer, Livro>) ois.readObject();
            int contadorLivros = ois.readInt();
            Map<Integer, ItemDePedido> mapDeItensDePedido = (Map<Integer, ItemDePedido>) ois.readObject();
            int contadorItensDePedido = ois.readInt();
            Map<Integer, ItemFaturado> mapDeItensFaturados = (Map<Integer, ItemFaturado>) ois.readObject();
            int contadorItensFaturados = ois.readInt();
            Map<Integer, Fatura> mapDeFaturas = (Map<Integer, Fatura>) ois.readObject();
            int contadorFaturas = ois.readInt();

            ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
            PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);
            LivroDAO livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);
            ItemDePedidoDAO itemDePedidoDAO = FabricaDeDaos.getDAO(ItemDePedidoDAO.class);
            ItemFaturadoDAO itemFaturadoDAO = FabricaDeDaos.getDAO(ItemFaturadoDAO.class);
            FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);

            clienteDAO.setMap(mapDeClientes);
            clienteDAO.setContador(contadorClientes);
            pedidoDAO.setMap(mapDePedidos);
            pedidoDAO.setContador(contadorPedidos);
            livroDAO.setMap(mapDeLivros);
            livroDAO.setContador(contadorLivros);
            itemDePedidoDAO.setMap(mapDeItensDePedido);
            itemDePedidoDAO.setContador(contadorItensDePedido);
            itemFaturadoDAO.setMap(mapDeItensFaturados);
            itemFaturadoDAO.setContador(contadorItensFaturados);
            faturaDAO.setMap(mapDeFaturas);
            faturaDAO.setContador(contadorFaturas);

            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("o arquivo não existe e será criado.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}