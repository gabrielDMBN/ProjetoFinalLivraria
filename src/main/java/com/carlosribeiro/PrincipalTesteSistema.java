package com.carlosribeiro;

import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.exception.*;
import com.carlosribeiro.model.*;
import com.carlosribeiro.service.*;
import com.carlosribeiro.util.FabricaDeDaos;
import corejava.Console;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrincipalTesteSistema
{
    //dao
    private final FaturaDAO faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);

    //serviços
    private final ClienteService clienteService = new ClienteService();
    private final PedidoService pedidoService = new PedidoService();
    private final ItemDePedidoService itemDePedidoService = new ItemDePedidoService();
    private final ItemFaturadoService itemFaturadoService = new ItemFaturadoService();
    private final FaturaService faturaService = new FaturaService();
    private final LivroService livroService = new LivroService();
    private final RelatorioService relatorioService = new RelatorioService();

    //principais
    PrincipalEnvioEmail envioEmail = new PrincipalEnvioEmail();
    private final PrincipalCliente principalCliente = new PrincipalCliente();
    private final PrincipalPedido principalPedido = new PrincipalPedido();
    private final PrincipalFatura principalFatura = new PrincipalFatura();

    //OPERAÇÕES DE TESTE
//    1) Cadastrar 5 livros:
//
//    isbn = 10, titulo = Aaa, descricao = Aaaa, qtdEstoque = 10, preco = 100.0  (o id será 1)
//    isbn = 20, titulo = Bbb, descricao = Bbbb, qtdEstoque = 20, preco = 200.0  (o id será 2)
//    isbn = 30, titulo = Ccc, descricao = Cccc, qtdEstoque = 30, preco = 300.0  (o id será 3)
//    isbn = 40, titulo = Ddd, descricao = Dddd, qtdEstoque = 40, preco = 400.0  (o id será 4)
//    isbn = 50, titulo = Eee, descricao = Eeee, qtdEstoque = 50, preco = 500.0  (o id será 5)
//
//    Para cadastrar um livro você deverá escrever o código abaixo:
//
//    Livro livro_1 = new Livro ("10", "Aaa", "Aaaa", 10, 100);
//livroService.incluir(livro_1);
//
//    Repita isso para todos os livros, clientes, pedidos, etc.
//
//        2) Listar os produtos cadastrados. Ao listar os produtos, o nome do livro e a quantidade em estoque deverão ser exibidos.
//
//    Para listar os livros, recupere todos eles com o método List<Livro> livros = livroService.recuperarLivros() e implemente uma iteração para listar todos eles.
//
//        3) Cadastrar 2 clientes.
//
//        cpf = 111, nome = Xxxx, email = xxxx@gmail.com, telefone = 11111111
//    cpf = 222, nome = Yyyy, email = yyyy@gmail.com, telefone = 22222222
//
//        4) Cadastrar 5 pedidos (ao cadastrar cada pedido a thread que simula o envio de email deverá ser executada):
//
//    cliente = 1, dataEmissao = 01/01/2025
//        (status = não faturado uma vez que o pedido acaba de ser cadastrado e
//    dataCancelamento = null já que não será informada)
//
//    id do livro = 1, qtdPedida = 5  (a qtdRestante será  5 e o preco cobrado será 100 - copiado do livro)
//    id do livro = 2, qtdPedida = 15
//
//    cliente = 1, dataEmissao = 02/01/2025
//    id do livro = 1, qtdPedida = 10
//    id do livro = 3, qtdPedida = 40
//
//    cliente = 1, dataEmissao = 03/01/2025
//
//    id do livro = 1, qtdPedida = 5
//    id do livro = 3, qtdPedida = 10
//
//    cliente = 1, dataEmissao = 04/01/2025
//
//    id do livro = 2, qtdPedida = 10
//    id do livro = 3, qtdPedida = 10
//    id do livro = 4, qtdPedida = 10
//
//    cliente = 1, dataEmissao = 05/01/2025
//
//    id do livro = 2, qtdPedida = 5
//    id do livro = 3, qtdPedida = 5
//    id do livro = 4, qtdPedida = 5
//
//        5) Listar todos os pedidos exibindo para cada pedido o seu status e as quantidades pedidas de cada livro.
//
//        6) Listar os Livros com suas respectivas quantidades em estoque. Deverão ser exibidos os seguintes dados:
//
//    id = 1, isbn = 10, titulo = Aaa, descricao = Aaaa, qtdEstoque = 10, preco = 100.0
//    id = 2, isbn = 20, titulo = Bbb, descricao = Bbbb, qtdEstoque = 20, preco = 200.0
//    id = 3, isbn = 30, titulo = Ccc, descricao = Cccc, qtdEstoque = 30, preco = 300.0
//    id = 4, isbn = 40, titulo = Ddd, descricao = Dddd, qtdEstoque = 40, preco = 400.0
//    id = 5, isbn = 50, titulo = Eee, descricao = Eeee, qtdEstoque = 50, preco = 500.0
//
//        7) Faturar os pedidos 1 e 2, nesta ordem, para o mês de janeiro de 2025.
//
//        8) Cancelar a fatura 2.
//
//    Não será possível pois para cancelar uma fatura o cliente já tem que ter faturado pelo menos 3 pedidos.
//
//9) Faturar os pedidos 3 e 4, nesta ordem, para o mês de janeiro de 2025.
//
//    O faturamento do pedido 3 não deverá gerar uma fatura (e o usuário deverá ser avisado) uma vez que o estoque estará zerado para os livros 1 e 3.
//
//        10) Faturar o pedido 5 para o mês de fevereiro de 2025.
//
//        11) Listar os Livros com suas respectivas quantidades em estoque. Deverão ser exibidos os seguintes dados:
//
//    id = 1, isbn = 10, titulo = Aaa, descricao = Aaaa, qtdEstoque = 0,  preco = 100.0
//    id = 2, isbn = 20, titulo = Bbb, descricao = Bbbb, qtdEstoque = 0,  preco = 200.0
//    id = 3, isbn = 30, titulo = Ccc, descricao = Cccc, qtdEstoque = 0,  preco = 300.0
//    id = 4, isbn = 40, titulo = Ddd, descricao = Dddd, qtdEstoque = 25, preco = 400.0
//    id = 5, isbn = 50, titulo = Eee, descricao = Eeee, qtdEstoque = 50, preco = 500.0
//
//        12) Listar todos as faturas. Deverão ser exibidos os dados abaixo.
//    OBS: O faturamento do pedido 3 não deverá gerar uma fatura.
//
//        Fatura 1,  valorTotalDaFatura = 5 x 100 + 15 x 200 = 3500, valorTotalDoDesconto = 0
//    Livro 1 - qtdFaturada = 5
//    Livro 2 - qtdFaturada = 15
//
//    Fatura 2,  valorTotalDaFatura = 5 x 100 + 30 x 300 = 9500, valorTotalDoDesconto = 0
//    Livro 1 - qtdFaturada = 5
//    Livro 3 - qtdFaturada = 30
//
//    Fatura 3,  valorTotalDaFatura = 5 x 200 + 10 x 400 = 5000, valorTotalDoDesconto = 0
//    Livro 2 - qtdFaturada = 5
//    Livro 4 - qtdFaturada = 10
//
//    Fatura 4,  valorTotalDaFatura = 5 x 400 = 2000, valorTotalDoDesconto = 100 (5% de desconto para a quarta fatura em diante)
//    Livro 4 - qtdFaturada = 5
//
//        13) Cancelar o pedido 5 informando a data de cancelamento: 06/01/2025.
//
//    Não será possível cancelar uma vez que ele já foi faturado.
//
//14) Cancelar a fatura 3 informando a data de cancelamento: 06/01/2025.
//
//    Deverá ser cancelada.
//    5 unidades do livro 2 e 10 unidades do livro 4 deverão ser retornadas para o estoque.
//
//        15) Remover a fatura 3.
//
//    Não será possível remover uma vez que ela está cancelada.
//
//        16) Remover a fatura 4.
//
//    Deverá ser removida.
//    5 unidades do livro 4 deverão ser retornados para o estoque.
//
//        17) Listar os Livros com suas respectivas quantidades em estoque. Deverão ser exibidos os seguintes dados:
//
//    id = 1, isbn = 10, titulo = Aaa, descricao = Aaaa, qtdEstoque =  0, preco = 100.0
//    id = 2, isbn = 20, titulo = Bbb, descricao = Bbbb, qtdEstoque =  5, preco = 200.0
//    id = 3, isbn = 30, titulo = Ccc, descricao = Cccc, qtdEstoque =  0, preco = 300.0
//    id = 4, isbn = 40, titulo = Ddd, descricao = Dddd, qtdEstoque = 40, preco = 400.0
//    id = 5, isbn = 50, titulo = Eee, descricao = Eeee, qtdEstoque = 50, preco = 500.0
//
//        18) Abastecer o estoque adicionando as quantidades abaixo aos livros:
//
//    Livro 1 - adicionar 100 unidades
//    Livro 2 - adicionar 200 unidades
//    Livro 3 - adicionar 300 unidades
//    Livro 4 - adicionar 400 unidades
//    Livro 5 - adicionar 500 unidades
//
//19) Listar os Livros com suas respectivas quantidades em estoque. Deverão ser exibidos os seguintes dados:
//
//    id = 1, isbn = 10, titulo = Aaa, descricao = Aaaa, qtdEstoque = 100, preco = 100.0
//    id = 2, isbn = 20, titulo = Bbb, descricao = Bbbb, qtdEstoque = 205, preco = 200.0
//    id = 3, isbn = 30, titulo = Ccc, descricao = Cccc, qtdEstoque = 300, preco = 300.0
//    id = 4, isbn = 40, titulo = Ddd, descricao = Dddd, qtdEstoque = 440, preco = 400.0
//    id = 5, isbn = 50, titulo = Eee, descricao = Eeee, qtdEstoque = 550, preco = 500.0
//
//        20) Faturar os pedidos 1 a 5, nesta ordem, para o mês de fevereiro de 2025.
//
//    Não será possível faturar o pedido 1 uma vez que já se encontra integralmente faturado.
//    Os demais pedidos (2 a 5) serão faturados.
//
//        20) Executar o relatório 1 - Lista contendo a quantidade faturada, o nome do livro e a data da fatura de itens faturados do livro 1 para janeiro de 2025.
//
//        21) Executar o relatório 2 - Lista dos produtos que nunca foram faturados. Deverá ser exibido o livro 5.
//
//        22) Executar o relatório 3 - Para o mês de fevereiro de 2025, a lista dos produtos faturados com a respectiva quantidade.
//
//        Observação: Nos casos dos itens 8, 9, 12, 13 e 15 acima, onde um método de serviço é chamado e uma exceção é propagada,
//         acrescente um try / catch no código escrito para testar essas funcionalidades, caso contrário a exceção gerada irá
//         explodir na tela e o teste do sistema será interrompido.

    public void principal()
    {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "TESTE DO SISTEMA");
            System.out.println('\n' + "========================================================");

            //começar o teste

            //1) Cadastrar 5 livros:
            Livro umLivro1 = new Livro("10", "Aaa", "Aaaa", 10, 100);
            livroService.incluir(umLivro1);
            System.out.println("\nLivro número " + umLivro1.getId() + " cadastrado com sucesso!");

            Livro umLivro2 = new Livro("20", "Bbb", "Bbbb", 20, 200);
            livroService.incluir(umLivro2);
            System.out.println("\nLivro número " + umLivro2.getId() + " cadastrado com sucesso!");

            Livro umLivro3 = new Livro("30", "Ccc", "Cccc", 30, 300);
            livroService.incluir(umLivro3);
            System.out.println("\nLivro número " + umLivro3.getId() + " cadastrado com sucesso!");

            Livro umLivro4 = new Livro("40", "Ddd", "Dddd", 40, 400);
            livroService.incluir(umLivro4);
            System.out.println("\nLivro número " + umLivro4.getId() + " cadastrado com sucesso!");

            Livro umLivro5 = new Livro("50", "Eee", "Eeee", 50, 500);
            livroService.incluir(umLivro5);
            System.out.println("\nLivro número " + umLivro5.getId() + " cadastrado com sucesso!");

            //2) Listar os produtos cadastrados. Ao listar os produtos, o nome do livro e a quantidade em estoque deverão ser exibidos.
            System.out.println("========================================================");
            List<Livro> livros = livroService.recuperarLivros();
            for (Livro livro : livros) {
                System.out.println(livro);
            }
            System.out.println("========================================================");

            //3) Cadastrar 2 clientes.
            Cliente umCliente1 = new Cliente("111", "Xxxx", "xxx@gmail.com", "11111111", "Rua X");
            clienteService.incluir(umCliente1);
            System.out.println("\nCliente número " + umCliente1.getId() + " cadastrado com sucesso!");

            Cliente umCliente2 = new Cliente("222", "Yyyy", "yyy@gmail.com", "22222222", "Rua Y");
            clienteService.incluir(umCliente2);
            System.out.println("\nCliente número " + umCliente2.getId() + " cadastrado com sucesso!");

            //4) Cadastrar 5 pedidos para o cliente 1 (ao cadastrar cada pedido a thread que simula o envio de email deverá ser execut
            Pedido umPedido1 = new Pedido(LocalDate.now(), null, "Processando", umCliente1, "Rua X");
            pedidoService.incluir(umPedido1);
            ItemDePedido itemDePedido1 = itemDePedidoService.incluirItemDePedido(umPedido1, umLivro1, 5);
            umPedido1.getItensDePedido().add(itemDePedido1);
            ItemDePedido itemDePedido2 = itemDePedidoService.incluirItemDePedido(umPedido1, umLivro2, 15);
            umPedido1.getItensDePedido().add(itemDePedido2);
            System.out.println("\nPedido número " + umPedido1.getId() + " cadastrado com sucesso!. Status: " + umPedido1.getStatus());
            List<ItemDePedido> itens1 = itemDePedidoService.recuperarItensDePedidoPorPedido(umPedido1.getId());
            for (ItemDePedido item : itens1) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            envioEmail.enviarEmail(umPedido1.getCliente());

            Pedido umPedido2 = new Pedido(LocalDate.now(), null, "Processando", umCliente1, "Rua X");
            pedidoService.incluir(umPedido2);
            ItemDePedido itemDePedido3 = itemDePedidoService.incluirItemDePedido(umPedido2, umLivro1, 10);
            umPedido2.getItensDePedido().add(itemDePedido3);
            ItemDePedido itemDePedido4 = itemDePedidoService.incluirItemDePedido(umPedido2, umLivro3, 40);
            umPedido2.getItensDePedido().add(itemDePedido4);
            System.out.println("\nPedido número " + umPedido2.getId() + " cadastrado com sucesso!. Status: " + umPedido2.getStatus());
            List<ItemDePedido> itens2 = itemDePedidoService.recuperarItensDePedidoPorPedido(umPedido2.getId());
            for (ItemDePedido item : itens2) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            envioEmail.enviarEmail(umPedido2.getCliente());

            Pedido umPedido3 = new Pedido(LocalDate.now(), null, "Processando", umCliente1, "Rua X");
            pedidoService.incluir(umPedido3);
            ItemDePedido itemDePedido5 = itemDePedidoService.incluirItemDePedido(umPedido3, umLivro1, 5);
            umPedido3.getItensDePedido().add(itemDePedido5);
            ItemDePedido itemDePedido6 = itemDePedidoService.incluirItemDePedido(umPedido3, umLivro3, 10);
            umPedido3.getItensDePedido().add(itemDePedido6);
            System.out.println("\nPedido número " + umPedido3.getId() + " cadastrado com sucesso!. Status: " + umPedido3.getStatus());
            List<ItemDePedido> itens3 = itemDePedidoService.recuperarItensDePedidoPorPedido(umPedido3.getId());
            for (ItemDePedido item : itens3) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            envioEmail.enviarEmail(umPedido3.getCliente());

            Pedido umPedido4 = new Pedido(LocalDate.now(), null, "Processando", umCliente1, "Rua X");
            pedidoService.incluir(umPedido4);
            ItemDePedido itemDePedido7 = itemDePedidoService.incluirItemDePedido(umPedido4, umLivro2, 10);
            umPedido4.getItensDePedido().add(itemDePedido7);
            ItemDePedido itemDePedido8 = itemDePedidoService.incluirItemDePedido(umPedido4, umLivro3, 10);
            umPedido4.getItensDePedido().add(itemDePedido8);
            ItemDePedido itemDePedido12 = itemDePedidoService.incluirItemDePedido(umPedido4, umLivro4, 10);
            umPedido4.getItensDePedido().add(itemDePedido12);
            System.out.println("\nPedido número " + umPedido4.getId() + " cadastrado com sucesso!. Status: " + umPedido4.getStatus());
            List<ItemDePedido> itens4 = itemDePedidoService.recuperarItensDePedidoPorPedido(umPedido4.getId());
            for (ItemDePedido item : itens4) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            envioEmail.enviarEmail(umPedido4.getCliente());


            Pedido umPedido5 = new Pedido(LocalDate.now(), null, "Processando", umCliente1, "Rua X");
            pedidoService.incluir(umPedido5);
            ItemDePedido itemDePedido9 = itemDePedidoService.incluirItemDePedido(umPedido5, umLivro2, 5);
            umPedido5.getItensDePedido().add(itemDePedido9);
            ItemDePedido itemDePedido10 = itemDePedidoService.incluirItemDePedido(umPedido5, umLivro3, 5);
            umPedido5.getItensDePedido().add(itemDePedido10);
            ItemDePedido itemDePedido11 = itemDePedidoService.incluirItemDePedido(umPedido5, umLivro4, 5);
            umPedido5.getItensDePedido().add(itemDePedido11);
            System.out.println("\nPedido número " + umPedido5.getId() + " cadastrado com sucesso!. Status: " + umPedido5.getStatus());
            List<ItemDePedido> itens5 = itemDePedidoService.recuperarItensDePedidoPorPedido(umPedido5.getId());
            for (ItemDePedido item : itens5) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            envioEmail.enviarEmail(umPedido5.getCliente());

            //5) Listar todos os pedidos exibindo para cada pedido o seu status e as quantidades pedidas de cada livro.
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "Listagem de pedidos:");
            System.out.println("\nPedido número " + umPedido1.getId() + ". Status: " + umPedido1.getStatus());
            for (ItemDePedido item : itens1) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }

            System.out.println("\nPedido número " + umPedido2.getId() + ". Status: " + umPedido2.getStatus());
            for (ItemDePedido item : itens2) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }

            System.out.println("\nPedido número " + umPedido3.getId() + ". Status: " + umPedido3.getStatus());
            for (ItemDePedido item : itens3) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }

            System.out.println("\nPedido número " + umPedido4.getId() + ". Status: " + umPedido4.getStatus());
            for (ItemDePedido item : itens4) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }

            System.out.println("\nPedido número " + umPedido5.getId() + ". Status: " + umPedido5.getStatus());
            for (ItemDePedido item : itens5) {
                System.out.println("Livro: " + item.getLivro().getTitulo() + " | Quantidade: " + item.getQtdPedida());
            }
            System.out.println('\n' + "========================================================");

            //6) Listar os Livros com suas respectivas quantidades em estoque.
            System.out.println('\n' + "========================================================");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
            System.out.println('\n' + "========================================================");

            //7) Faturar os pedidos 1 e 2, nesta ordem
            try {
                System.out.println("Faturando pedido " + umPedido1.getId());
                Fatura fatura1 = itemFaturadoService.faturarPedido(umPedido1);
            } catch (NenhumItemFaturadoException | StatusIndevidoException e) {
                System.out.println(e.getMessage());
            }

            try {
                System.out.println("Faturando pedido " + umPedido2.getId());
                Fatura fatura2 = itemFaturadoService.faturarPedido(umPedido2);
            } catch (NenhumItemFaturadoException | StatusIndevidoException e) {
                System.out.println(e.getMessage());
            }

            //8) Cancelar a fatura 2.
            try {
                System.out.println("Cancelando fatura 2...");
                faturaService.cancelarFatura(2, umCliente1.getId());
                System.out.println("Fatura cancelada com sucesso!");
            } catch (EntidadeNaoEncontradaException | StatusIndevidoException |
                     TentativaAcessoIndevidoException e) {
                System.out.println('\n' + e.getMessage());
            }

            //9) Faturar os pedidos 3 e 4, nesta ordem, para o mês de janeiro de 2025.

            try {
                System.out.println("Faturando pedido " + umPedido3.getId());
                Fatura fatura3 = itemFaturadoService.faturarPedido(umPedido3);
            }
            catch (NenhumItemFaturadoException | StatusIndevidoException e){
                System.out.println(e.getMessage());
            }

            try {
                System.out.println("Faturando pedido " + umPedido4.getId());
                Fatura fatura4 = itemFaturadoService.faturarPedido(umPedido4);
            }
            catch (NenhumItemFaturadoException | StatusIndevidoException e){
                System.out.println(e.getMessage());
            }

            //10) Faturar o pedido 5
            try {
                System.out.println("Faturando pedido " + umPedido5.getId());
                Fatura fatura5 = itemFaturadoService.faturarPedido(umPedido5);
            }
            catch (NenhumItemFaturadoException | StatusIndevidoException e){
                System.out.println(e.getMessage());
            }

            //11) Listar os Livros com suas respectivas quantidades em estoque.
            System.out.println("========================================================");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
            System.out.println("========================================================");

            //12) Listar todos as faturas.
            List<Fatura> faturas = faturaService.recuperarTodasAsFaturasDeUmCliente(umCliente1.getId());
            if (faturas.isEmpty()) {
                System.out.println("Nenhuma fatura encontrada para o cliente ID: " + umCliente1.getId());
            } else {
                System.out.println("Faturas do cliente ID: " + umCliente1.getId());
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

            //13) Cancelar o pedido 5;
            try {
                System.out.println("Cancelando pedido " + umPedido5.getId());
                pedidoService.cancelarPedido(umPedido5.getId(), umCliente1.getId());
            } catch (EntidadeNaoEncontradaException | StatusIndevidoException e) {
                System.out.println(e.getMessage());
            }

            //14) Cancelar a fatura 3
            try {
                System.out.println("Cancelando fatura 3...");
                faturaService.cancelarFatura(3, umCliente1.getId());
                System.out.println("Fatura cancelada com sucesso!");
            } catch (EntidadeNaoEncontradaException | StatusIndevidoException | TentativaAcessoIndevidoException e) {
                System.out.println('\n' + e.getMessage());
            }

            //15) Remover a fatura 3
            try {
                System.out.println("Removendo fatura 3...");
                faturaService.remover(3, umCliente1.getId());
                System.out.println("Fatura removida com sucesso!");
            } catch (EntidadeNaoEncontradaException | StatusIndevidoException | TentativaAcessoIndevidoException e) {
                System.out.println('\n' + e.getMessage());
            }

            //16) Remover a fatura 4
            try {
                System.out.println("Removendo fatura 4...");
                faturaService.remover(4, umCliente1.getId());
                System.out.println("Fatura removida com sucesso!");
            } catch (EntidadeNaoEncontradaException | StatusIndevidoException | TentativaAcessoIndevidoException e) {
                System.out.println('\n' + e.getMessage());
            }

            //17) Listar os Livros com suas respectivas quantidades em estoque.
            System.out.println("========================================================");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
            System.out.println("========================================================");

            //18) Abastecer o estoque
            System.out.println("========================================================");
            System.out.println("Abastecendo o estoque...");
            System.out.println("========================================================");
            livroService.alterarQtdEstoque(1, umLivro1.getQtdEstoque() + 100);
            livroService.alterarQtdEstoque(2, umLivro2.getQtdEstoque() + 200);
            livroService.alterarQtdEstoque(3, umLivro3.getQtdEstoque() + 300);
            livroService.alterarQtdEstoque(4, umLivro4.getQtdEstoque() + 400);
            livroService.alterarQtdEstoque(5, umLivro5.getQtdEstoque() + 500);

            //19) Listar os Livros com suas respectivas quantidades em estoque.
            System.out.println("========================================================");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
            System.out.println("========================================================");

            //20) Faturar os pedidos 1 a 5, nesta ordem.
            List<Pedido> pedidos = pedidoService.recuperarTodosOsPedidosDeUmCliente(umCliente1.getId());
            for (Pedido pedido : pedidos) {
                try {
                    System.out.println("Faturando pedido " + pedido.getId());
                    Fatura fatura = itemFaturadoService.faturarPedido(pedido);
                } catch (NenhumItemFaturadoException | StatusIndevidoException e) {
                    System.out.println(e.getMessage());
                }
            }

            //21) Executar o relatório 1
            System.out.println("========================================================");
            var itens = relatorioService.getItensFaturadosPorLivroEMes(1, 1, 2025);
            System.out.println("Livro " + 1 + " faturados no mês " + 01 + " do ano " + 2025 + ":");
            if (itens.isEmpty()) {
                System.out.println("Nenhum item faturado encontrado.");
            } else {
                for (ItemFaturado item : itens) {
                    System.out.println("Livro = " + item.getItemDePedido().getLivro().getTitulo() +
                            "  Quantidade Faturada = " + item.getQtdFaturada() +
                            "  Data da Fatura = " + item.getFatura().getDataEmissao());
                }
            }
            System.out.println("========================================================");

            //22) Executar o relatório 2
            System.out.println("========================================================");
            var livrosNFaturados = relatorioService.getLivrosNuncaFaturados();
            System.out.println("Livros nunca faturados:");
            livrosNFaturados.forEach(livro -> System.out.println(livro.getTitulo()));
            System.out.println("========================================================");

            //23) Executar o relatório 3
            System.out.println("========================================================");
            var livrosFaturados = relatorioService.getLivrosFaturadosPorMesEAno(1, 2025);
            System.out.println("Livros faturados no mês " + 01 + " do ano " + 2025 + ":");
            if (livrosFaturados.isEmpty()) {
                System.out.println("Nenhum livro faturado encontrado.");
            }

            Map<String, Integer> consolidado = new HashMap<>();

            // Itera sobre os itens faturados e atualiza o mapa com a quantidade faturada de cada livro
            for (ItemDePedido item : livrosFaturados) {
                String nomeLivro = item.getLivro().getTitulo();
                int quantidade = item.getQtdPedida() - item.getQtdAFaturar();

                consolidado.put(nomeLivro, consolidado.getOrDefault(nomeLivro, 0) + quantidade);
            }
            consolidado.forEach((nome, quantidade) -> System.out.println("Produto: " + nome + " | Quantidade: " + quantidade));
            System.out.println("========================================================");


            System.out.println("Teste finalizado!");


            //finalizar o teste
            continua = false;
    }
}

}
