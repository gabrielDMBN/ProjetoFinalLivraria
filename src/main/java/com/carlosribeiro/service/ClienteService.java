package com.carlosribeiro.service;

import com.carlosribeiro.dao.ClienteDAO;
import com.carlosribeiro.dao.PedidoDAO;
import com.carlosribeiro.exception.EntidadeNaoEncontradaException;
import com.carlosribeiro.model.Cliente;
import com.carlosribeiro.model.Pedido;
import com.carlosribeiro.util.FabricaDeDaos;

import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO = FabricaDeDaos.getDAO(ClienteDAO.class);
    private final PedidoDAO pedidoDAO = FabricaDeDaos.getDAO(PedidoDAO.class);

    public Cliente incluir(Cliente cliente) {
        return clienteDAO.incluir(cliente);
    }

    public Cliente alterarCpf(int id, String novoCpf) {
        Cliente cliente = recuperarClientePorId(id);
        cliente.setCpf(novoCpf);
        return cliente;
    }

    public Cliente alterarNome(int id, String novoNome) {
        Cliente cliente = recuperarClientePorId(id);
        cliente.setNome(novoNome);
        return cliente;
    }

    public Cliente alterarEmail(int id, String novoEmail) {
        Cliente cliente = recuperarClientePorId(id);
        cliente.setEmail(novoEmail);
        return cliente;
    }

    public Cliente alterarTelefone(int id, String novoTelefone) {
        Cliente cliente = recuperarClientePorId(id);
        cliente.setTelefone(novoTelefone);
        return cliente;
    }

    public void remover(int id) {
        Cliente cliente = recuperarClientePorId(id);
        List<Pedido> pedidos = pedidoDAO.recuperarTodosOsPedidosDeUmCliente(id);
        if (!pedidos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Este cliente possui pedidos e não pode ser removido.");
        }
        clienteDAO.remover(id);
    }

    public Cliente recuperarClientePorId(int id) {
        Cliente cliente = clienteDAO.recuperarPorId(id);
        if (cliente == null) {
            throw new EntidadeNaoEncontradaException("Cliente inexistente.");
        }
        return cliente;
    }

    public List<Cliente> recuperarClientes() {
        return clienteDAO.recuperarTodos();
    }

    public List<Cliente> recuperarClientesOrdenadosPorNome() {
        return clienteDAO.recuperarClientesOrdenadosPorNome();
    }
}