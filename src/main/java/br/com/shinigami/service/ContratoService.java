package br.com.shinigami.service;


import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Contrato;
import br.com.shinigami.repository.ContratoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContratoService implements ServiceInterface<Contrato> {

    private final ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Override
    public boolean adicionar(Contrato contrato) throws RegraDeNegocioException, BancoDeDadosException {
            contratoRepository.adicionar(contrato);
            return true;
    }

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
            boolean conseguiuRemover = contratoRepository.remover(id);
            return conseguiuRemover;
    }

    @Override
    public void editar(Integer id, Contrato contrato) throws RegraDeNegocioException, BancoDeDadosException {
            contratoRepository.editar(id, contrato);
    }

    @Override
    public void listar() throws BancoDeDadosException {
          List<Contrato> listar = contratoRepository.listar();
          listar.forEach(contrato -> {
              System.out.println("id:" + contrato.getIdContrato() +
                      " | Nome Locatario: " + contrato.getLocatario().getNome() +
                      " | Nome Locador: " + contrato.getLocador().getNome());
          });

    }

    public Contrato buscarContrato(int id) throws RegraDeNegocioException, BancoDeDadosException {
            return contratoRepository.buscarContrato(id);
    }


}