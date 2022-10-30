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
public class ImovelService implements ServiceInterface<ImovelDTO, ImovelCreateDTO> {
    private final ImovelRepository imovelRepository;
    private final EnderecoService enderecoService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    @Override
    public List<ImovelDTO> list() throws RegraDeNegocioException {
        try {
            List<Imovel> listar = imovelRepository.list();
            return listar.stream()
                    .map(imovel -> objectMapper.convertValue(imovel, ImovelDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar imoveis!");
        }
    }

    public ImovelDTO buscarImovel(Integer id) throws RegraDeNegocioException {
        try {
            log.info("Buscando imovel...");
            Imovel imovel = imovelRepository.buscarImovel(id);
            if (imovel == null) {
                throw new RegraDeNegocioException("Imovel não encontrando!");
            }
            log.info("Imovel encontrado!!");
            ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);

            return imovelDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Imovel não encontrado!");
        }
    }

    @Override
    public ImovelDTO create(ImovelCreateDTO imovel) throws RegraDeNegocioException {
        try {
            log.info("Criando Imovel...");
            Imovel imovelEntity = imovelRepository.create(objectMapper.convertValue(imovel, Imovel.class));

            log.info("Imovel Criado!!");

            ImovelDTO imovelDTO = objectMapper.convertValue(imovelEntity, ImovelDTO.class);

            return imovelDTO;

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar Imovel");
        }
    }

    @Override
    public ImovelDTO update(Integer id, ImovelCreateDTO imovel) throws RegraDeNegocioException {
        try {
            Imovel imovelEntity;
            if (imovelRepository.buscarImovel(id) == null) {
                throw new RegraDeNegocioException("Imovel Não Encontrado");
            }
            imovelEntity = imovelRepository.update(id,objectMapper.convertValue(imovel, Imovel.class));

            log.info("Editando Imovel");

            ImovelDTO imovelDTO = objectMapper.convertValue(imovelEntity, ImovelDTO.class);
            return imovelDTO;

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao atualizar imovel!");
        }
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            if (imovelRepository.buscarImovel(id) == null) {
                throw new RegraDeNegocioException("Imovel não Encontrado!");
            }
            log.info("Deletando Imovel...");
            imovelRepository.delete(id);
            log.info("Imovel Deletado!!");
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar imovel!");
        }
    }

    public List<ImovelDTO> listarImoveisDisponiveis() throws RegraDeNegocioException {
        try {
            List<Imovel> listar = imovelRepository.listarImoveisDisponiveis();
            return listar.stream()
                    .map(imovel -> objectMapper.convertValue(imovel, ImovelDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar imoveis disponiveis!");
        }
    }

}

