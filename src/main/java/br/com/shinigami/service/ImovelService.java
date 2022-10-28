package br.com.shinigami.service;


import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.repository.ImovelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImovelService {
    private final ImovelRepository imovelRepository;

    private final ObjectMapper objectMapper;

    public void adicionar(ImovelCreateDTO imovel) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando Imovel...");
            imovelRepository.create(objectMapper.convertValue(imovel,Imovel.class));
        log.info("Imovel Criado!!");
    }


    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException{

        if(imovelRepository.buscarImovel(id)==null){
            throw new RegraDeNegocioException("Imovel não Encontrado!");
        }
            log.info("Deletando Imovel...");
            imovelRepository.delete(id);
            log.info("Imovel Deletado!!");
    }


    public Imovel editar(Integer id, ImovelCreateDTO imovel) throws RegraDeNegocioException, BancoDeDadosException {
            if(imovelRepository.buscarImovel(id)==null){
                throw new RegraDeNegocioException("Imovel Não Encontrado");
            }
            log.info("Editando Imovel");
            return imovelRepository.update(id, objectMapper.convertValue(imovel,Imovel.class));

    }


    public List<ImovelDTO> listar() throws BancoDeDadosException{
            List<Imovel> listar = imovelRepository.list();
            return listar.stream()
                    .map(imovel -> objectMapper.convertValue(imovel,ImovelDTO.class))
                    .toList();
    }

    public List<ImovelDTO> listarImoveisDisponiveis() throws RegraDeNegocioException, BancoDeDadosException{
            List<Imovel> listar = imovelRepository.listarImoveisDisponiveis();
            return listar.stream()
                    .map(imovel -> objectMapper.convertValue(imovel,ImovelDTO.class))
                    .toList();
    }

    public ImovelDTO buscarImovel(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Buscando imovel...");
        Imovel imovel = imovelRepository.buscarImovel(id);
        if(imovel == null){
            throw new RegraDeNegocioException("Imovel não encontrando!");
        }
        log.info("Imovel encontrado!!");
        return objectMapper.convertValue(imovel,ImovelDTO.class);

    }
}

