package com.carlosribeiro.service;

import com.carlosribeiro.dao.ClienteDAO;
import com.carlosribeiro.dao.FaturaDAO;
import com.carlosribeiro.dao.ItemDePedidoDAO;
import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.model.*;
import com.carlosribeiro.util.FabricaDeDaos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioService {
    private final ItemDePedidoDAO itemDePedidoDAO;
    private final LivroDAO livroDAO;
    private final ClienteDAO clienteDAO;
    private final FaturaDAO faturaDAO;

    public RelatorioService() {
        this.itemDePedidoDAO = FabricaDeDaos.getDAO(ItemDePedidoDAO.class);
        this.livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);
        this.clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
        this.faturaDAO = FabricaDeDaos.getDAO(FaturaDAO.class);
    }

    public List<ItemFaturado> getItensFaturadosPorLivroEMes(int livroId, int mes, int ano) {
        List<ItemFaturado> itensFaturadosNoMesEAno = new ArrayList<>();
        List<Cliente> todosClientes = clienteDAO.recuperarTodos();

        for (Cliente cliente : todosClientes) {
            List<Fatura> faturasDoCliente = faturaDAO.recuperarTodasAsFaturasDeUmCliente(cliente.getId());

            for (Fatura fatura : faturasDoCliente) {
                LocalDate dataFatura = fatura.getDataEmissao();
                if (dataFatura.getMonthValue() == mes && dataFatura.getYear() == ano ) {
                    for (ItemFaturado itemFaturado : fatura.getItensFaturados()) {
                        ItemDePedido itemDePedido = itemDePedidoDAO.recuperarPorId(itemFaturado.getItemDePedido().getId());
                        if (itemDePedido.getLivro().getId() == livroId) {
                            itensFaturadosNoMesEAno.add(itemFaturado);
                        }
                    }
                }
            }
        }

        return itensFaturadosNoMesEAno;
    }

    //esse metodo retorna os livros e litra quais tem a lista ItemDePedido com listas vazias de ItemFaturado
    public List<Livro> getLivrosNuncaFaturados() {
        List<Livro> todosLivros = livroDAO.recuperarTodos();
        List<Livro> livrosNuncaFaturados = new ArrayList<>();

        for (Livro livro : todosLivros) {
            //boolean hasItemDePedido = !livro.getItemDePedidos().isEmpty();

            boolean nuncaFaturado = true;
            for (ItemDePedido item : livro.getItemDePedidos()) { //para cada item de pedido do livro
                if (!item.getItensFaturados().isEmpty()) { //se o item de pedido tem itens faturados
                    nuncaFaturado = false;
                    break;
                }
            }

            if (nuncaFaturado) {
                livrosNuncaFaturados.add(livro);
            }
        }

        return livrosNuncaFaturados;
    }

    //esse metodo retorna os itens de pedido que foram faturados em um determinado mes e ano
    public List<ItemDePedido> getLivrosFaturadosPorMesEAno(int mes, int ano) {
        List<ItemDePedido> itensFaturadosNoMesEAno = new ArrayList<>();
        List<Livro> todosLivros = livroDAO.recuperarTodos();

        for (Livro livro : todosLivros) {
            List<ItemFaturado> itensFaturados = getItensFaturadosPorLivroEMes(livro.getId(), mes, ano); //recupera os itens faturados do livro
            for (ItemFaturado itemFaturado : itensFaturados) { //para cada item faturado
                ItemDePedido itemDePedido = itemDePedidoDAO.recuperarPorId(itemFaturado.getItemDePedido().getId()); //recupera o item de pedido
                if (!itensFaturadosNoMesEAno.contains(itemDePedido)) { //se o item de pedido ainda não foi adicionado
                    itensFaturadosNoMesEAno.add(itemDePedido); //adiciona o item de pedido
                }
            }
        }

        return itensFaturadosNoMesEAno;
    }



}