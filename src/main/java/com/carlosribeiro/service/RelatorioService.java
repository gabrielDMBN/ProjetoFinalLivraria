package com.carlosribeiro.service;

import com.carlosribeiro.model.ItemDePedido;
import com.carlosribeiro.model.Livro;
import com.carlosribeiro.dao.ItemDePedidoDAO;
import com.carlosribeiro.dao.LivroDAO;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioService {
    private final ItemDePedidoDAO itemDePedidoDAO;
    private final LivroDAO livroDAO;

    public RelatorioService() {
        this.itemDePedidoDAO = FabricaDeDaos.getDAO(ItemDePedidoDAO.class);
        this.livroDAO = FabricaDeDaos.getDAO(LivroDAO.class);
    }

    public List<ItemDePedido> getItensFaturadosPorLivroEMes(int livroId, int mes, int ano) {
       return null;
    }

    //esse metodo retorna os livros e litra quais tem a lista ItemDePedido com listas vazias de ItemFaturado
    public List<Livro> getLivrosNuncaFaturados() {
        List<Livro> todosLivros = livroDAO.recuperarTodos();
        List<Livro> livrosNuncaFaturados = new ArrayList<>();

        for (Livro livro : todosLivros) {
            boolean hasItemDePedido = !livro.getItemDePedidos().isEmpty();

            boolean nuncaFaturado = true;
            for (ItemDePedido item : livro.getItemDePedidos()) {
                if (!item.getItensFaturados().isEmpty()) {
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

    public List<ItemDePedido> getLivrosFaturadosPorMesEAno(int mes, int ano) {
      return null;
    }
}