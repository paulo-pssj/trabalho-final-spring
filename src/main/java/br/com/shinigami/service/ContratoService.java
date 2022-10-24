package br.com.shinigami.service;


import br.com.shinigami.dto.Contrato.ContratoCreateDTO;
import br.com.shinigami.dto.Contrato.ContratoDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Contrato;
import br.com.shinigami.repository.ContratoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ObjectMapper objectMapper;

    public ContratoDTO create(ContratoCreateDTO contrato) throws RegraDeNegocioException, BancoDeDadosException {
        Contrato contratoNovo = objectMapper.convertValue(contrato, Contrato.class);
        log.info("Criando contrato...");
        Contrato contratoAdicionado = contratoRepository.adicionar(contratoNovo);
        log.info("Contrato criado com sucesso!");
        return objectMapper.convertValue(contratoAdicionado, ContratoDTO.class);
    }


    public void remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Removendo Contrato...");
        if(contratoRepository.buscarContrato(id)==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contratoRepository.remover(id);
        log.info("Contrato Removido!");
    }


    public void editar(Integer id, ContratoCreateDTO contrato) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Editando Contrato...");
        if(contratoRepository.buscarContrato(id)==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contratoRepository.editar(id, objectMapper.convertValue(contrato,Contrato.class));
        log.info("Contrato Editado!");
    }


    public List<ContratoDTO> listar() throws BancoDeDadosException {
        List<Contrato> listar = contratoRepository.listar();
        return listar.stream()
                .map(contrato -> objectMapper.convertValue(contrato,ContratoDTO.class))
                .toList();
    }

    public ContratoDTO buscarContrato(int id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Buscando Contrato...");
        Contrato contrato = contratoRepository.buscarContrato(id);
        if(contrato==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        log.info("Contrato encontrado!!");
        return objectMapper.convertValue(contrato,ContratoDTO.class);
    }


}