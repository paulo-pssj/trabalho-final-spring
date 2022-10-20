package br.com.shinigami.service;

import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteService implements ServiceInterface<Cliente> {


    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean adicionar(Cliente pessoa) throws RegraDeNegocioException,BancoDeDadosException {

            Cliente pessoaAdicionada = clienteRepository.adicionar(pessoa);
            return true;
    }

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException,BancoDeDadosException {
            boolean conseguiuRemover = clienteRepository.remover(id);
            return conseguiuRemover;
    }

    @Override
    public void editar(Integer id, Cliente pessoa) throws RegraDeNegocioException,BancoDeDadosException {
        try {
            boolean conseguiuEditar = clienteRepository.editar(id, pessoa);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() throws BancoDeDadosException {
            List<Cliente> listar = clienteRepository.listar();
            listar.forEach(cliente -> {
                System.out.println("id:" + cliente.getIdCliente() + " | Nome: " + cliente.getNome());
            });
    }

    public Cliente buscarCliente(String busca) throws RegraDeNegocioException,BancoDeDadosException {
            int id = Integer.parseInt(busca.trim());
            return clienteRepository.buscarCliente(id);
    }
//
//    public boolean validaCpf(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getCpf().length() != 11) {
//            throw new DadoInvalidoException("CPF Invalido!");
//        }
//        return true;
//    }
//
//    public boolean validaNome(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getNome().equals(null) || cliente.getNome().isBlank()) {
//            throw new DadoInvalidoException("Nome Invalido!");
//        }
//        return true;
//    }
//
//    public boolean validaEmail(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getEmail().isBlank() || !cliente.getEmail().contains("@")) {
//            throw new DadoInvalidoException("Email invalido!");
//        }
//        return true;
//    }


}