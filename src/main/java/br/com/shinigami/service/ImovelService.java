package br.com.shinigami.service;


import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.repository.ImovelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService implements ServiceInterface<Imovel> {
    private final ImovelRepository imovelRepository;

    public ImovelService(ImovelRepository imovelRepository) {
        this.imovelRepository = imovelRepository;
    }

    @Override
    public boolean adicionar(Imovel imovel) throws RegraDeNegocioException, BancoDeDadosException {
            imovelRepository.adicionar(imovel);
            return true;
    }

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException{
            boolean conseguiuRemover = imovelRepository.remover(id);
            return conseguiuRemover;
    }

    @Override
    public void editar(Integer id, Imovel imovel) throws RegraDeNegocioException, BancoDeDadosException {
            imovelRepository.editar(id, imovel);
    }

    @Override
    public void listar() throws BancoDeDadosException{
            List<Imovel> listar = imovelRepository.listar();
            listar.forEach(imovel -> {
                System.out.println("id: " + imovel.getIdImovel() + "| Dono: " + imovel.getDono().getNome() + "| Alugado: " + (imovel.isAlugado() ? "SIM" : "NÃO"));
            });
    }

    public void listarImoveisDisponiveis() throws RegraDeNegocioException, BancoDeDadosException{
            List<Imovel> listar = imovelRepository.listarImoveisDisponiveis();
            listar.forEach(imovel -> {
                System.out.println("id: " + imovel.getIdImovel() + "| Dono: " + imovel.getDono().getNome() + "| Alugado: " + (imovel.isAlugado() ? "SIM" : "NÃO"));
            });

    }

    public Imovel buscarImovel(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
            return imovelRepository.buscarImovel(id);

    }
}

