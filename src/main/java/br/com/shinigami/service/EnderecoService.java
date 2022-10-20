package br.com.shinigami.service;


import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EnderecoService implements ServiceInterface<Endereco> {
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public boolean adicionar(Endereco endereco) throws RegraDeNegocioException, BancoDeDadosException {
            enderecoRepository.adicionar(endereco);
            System.out.println("Endereco adicinado com sucesso!");
            return true;

    }

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            boolean conseguiuRemover = enderecoRepository.remover(id);
            return conseguiuRemover;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void editar(Integer id, Endereco endereco) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            boolean conseguiuEditar = enderecoRepository.editar(id, endereco);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() throws BancoDeDadosException {
        List<Endereco> listar = enderecoRepository.listar();
        listar.forEach(endereco -> {
            System.out.println("id:" + endereco.getIdEndereco() + " | Rua: " + endereco.getRua() + " | Estado: " + endereco.getEstado() + " | CEP: " + endereco.getCep());

        });
    }
}
