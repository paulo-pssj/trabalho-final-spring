package br.com.shinigami.service;


import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
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
        Contrato contratoAdicionado = contratoRepository.create(contratoNovo);
        log.info("Contrato criado com sucesso!");
        return objectMapper.convertValue(contratoAdicionado, ContratoDTO.class);
    }


    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        if(contratoRepository.buscarContrato(id)==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contratoRepository.delete(id);
        log.info("Contrato Removido!");
    }


    public ContratoDTO update(Integer id, ContratoCreateDTO contrato) throws RegraDeNegocioException, BancoDeDadosException {
        if(contratoRepository.buscarContrato(id)==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        Contrato contratoett = contratoRepository.update(id, objectMapper.convertValue(contrato,Contrato.class));
        log.info("Contrato Editado!");
        return objectMapper.convertValue(contratoett,ContratoDTO.class);
    }


    public List<ContratoDTO> list() throws BancoDeDadosException {
        List<Contrato> listar = contratoRepository.list();
        return listar.stream()
                .map(contrato -> objectMapper.convertValue(contrato,ContratoDTO.class))
                .toList();
    }

    public ContratoDTO buscarContrato(int id) throws RegraDeNegocioException, BancoDeDadosException {
        Contrato contrato = contratoRepository.buscarContrato(id);
        if(contrato==null){
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        log.info("Contrato encontrado!!");
        return objectMapper.convertValue(contrato,ContratoDTO.class);
    }


}