package br.com.shinigami.service;


import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Contrato;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.repository.ContratoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContratoService implements ServiceInterface<ContratoDTO,ContratoCreateDTO>{

    private final ContratoRepository contratoRepository;
    private final ImovelService imovelService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Override
    public ContratoDTO create(ContratoCreateDTO contrato) throws RegraDeNegocioException {
        try {
            Contrato contratoNovo = objectMapper.convertValue(contrato, Contrato.class);
            ImovelDTO imovel = imovelService.buscarImovel(contrato.getIdImovel());
            Contrato contratoAdicionado = contratoRepository.create(contratoNovo);
            log.info("Contrato criado com sucesso!");
            ContratoDTO contratoDto = objectMapper.convertValue(contratoAdicionado, ContratoDTO.class);

            emailService.sendEmailContrato(contratoDto);
            return contratoDto;
        } catch(BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar um contrato!");
        }
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            if (contratoRepository.buscarContrato(id) == null) {
                throw new RegraDeNegocioException("Contrato não encontrado!");
            }
            contratoRepository.delete(id);
            log.info("Contrato Removido!");
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar o contrato!");
        }
    }

    @Override
    public ContratoDTO update(Integer id, ContratoCreateDTO contrato) throws RegraDeNegocioException {
        try {
            if (contratoRepository.buscarContrato(id) == null) {
                throw new RegraDeNegocioException("Contrato não encontrado!");
            }
            Contrato contratoett = contratoRepository.update(id, objectMapper.convertValue(contrato, Contrato.class));
            log.info("Contrato Editado!");
            return objectMapper.convertValue(contratoett, ContratoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao atualizar o contrato!");
        }
    }

    @Override
    public List<ContratoDTO> list() throws RegraDeNegocioException {
        try {
            List<Contrato> listar = contratoRepository.list();
            return listar.stream()
                    .map(contrato -> objectMapper.convertValue(contrato, ContratoDTO.class))
                    .toList();
        }catch (BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro ao listar os contratos!");
        }
    }

    public ContratoDTO buscarContrato(int id) throws RegraDeNegocioException {
        try {
            Contrato contrato = contratoRepository.buscarContrato(id);
            if (contrato == null) {
                throw new RegraDeNegocioException("Contrato não encontrado!");
            }
            log.info("Contrato encontrado!!");
            return objectMapper.convertValue(contrato, ContratoDTO.class);
        } catch(BancoDeDadosException e) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
    }
}