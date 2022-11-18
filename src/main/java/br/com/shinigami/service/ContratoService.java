package br.com.shinigami.service;


import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.entity.ContratoEntity;
import br.com.shinigami.entity.ImovelEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ContratoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContratoService implements ServiceInterface<ContratoDTO, ContratoCreateDTO> {

    private final ContratoRepository contratoRepository;
    private final ImovelService imovelService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final ClienteService clienteService;

    @Override
    public ContratoDTO create(ContratoCreateDTO contrato) throws RegraDeNegocioException {
        ContratoEntity contratoEntityNovo = objectMapper.convertValue(contrato, ContratoEntity.class);
        ImovelEntity imovelEntity = imovelService.findById(contrato.getIdImovel());
        contratoEntityNovo.setAtivo(Tipo.S);
        contratoEntityNovo.setImovel(imovelEntity);
        contratoEntityNovo.setLocador(clienteService.findById(imovelEntity.getIdDono()));
        contratoEntityNovo.setLocatario(clienteService.findById(contrato.getIdLocatario()));
        contratoEntityNovo.setValorAluguel(imovelEntity.getValorMensal() + imovelEntity.getCondominio());

        imovelService.alugarImovel(imovelEntity);
        ContratoEntity contratoEntityAdicionado = contratoRepository.save(contratoEntityNovo);

        ContratoDTO contratoDTO = converteParaContratoDTO(contratoEntityAdicionado);

        String emailBase = "Contrato criado com sucesso! <br> Contrato entre: " +
                "<br> locador: " + contratoDTO.getLocador().getNome() +
                "<br> locatario: " + contratoDTO.getLocatario().getNome() +
                "<br>" + "Valor Mensal: R$" + contratoDTO.getImovel().getValorMensal();

        String assunto = "Seu contrato foi gerado com sucesso!!";

        emailService.sendEmail(contratoDTO.getLocador(), emailBase, assunto);
        emailService.sendEmail(contratoDTO.getLocatario(), emailBase, assunto);
        return contratoDTO;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        ContratoEntity contratoEntity = objectMapper.convertValue(contratoRepository.findById(id), ContratoEntity.class);
        if (contratoEntity == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contratoEntity.setAtivo(Tipo.N);
        imovelService.alugarImovel(imovelService.findById(contratoEntity.getIdImovel()));
        contratoRepository.save(contratoEntity);
    }

    @Override
    public ContratoDTO update(Integer id, ContratoCreateDTO contratoAtualizar) throws RegraDeNegocioException {
        ContratoEntity contratoEntityBusca = objectMapper.convertValue(contratoRepository.findById(id), ContratoEntity.class);
        if (contratoEntityBusca == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        ImovelEntity imovelEntityAntigo = imovelService.findById(contratoEntityBusca.getIdImovel());
        imovelService.alugarImovel(imovelEntityAntigo);
        ImovelEntity imovelEntityNovo = imovelService.findById(contratoAtualizar.getIdImovel());
        imovelService.alugarImovel(imovelEntityNovo);

        ContratoEntity contratoEntity = objectMapper.convertValue(contratoEntityBusca, ContratoEntity.class);
        contratoEntity.setImovel(imovelEntityNovo);
        contratoEntity.setLocatario(clienteService.findById(contratoAtualizar.getIdLocatario()));
        contratoEntity.setDataEntrada(contratoEntity.getDataEntrada());
        contratoEntity.setDataVencimento(contratoAtualizar.getDataVencimento());
        contratoRepository.save(contratoEntity);
        return converteParaContratoDTO(contratoEntity);
    }


    public PageDTO<ContratoDTO> list(Integer page) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<ContratoEntity> pageContrato = contratoRepository.findAllByAtivo(Tipo.S, pageRequest);
        List<ContratoDTO> listar = pageContrato.getContent()
                .stream()
                .map(contrato -> converteParaContratoDTO(contrato))
                .toList();

        return new PageDTO<>(pageContrato.getTotalElements(),
                pageContrato.getTotalPages(),
                page, pageRequest.getPageSize(),
                listar);
    }

    public List<RelatorioContratoClienteDTO> relatorioContratoCliente(Integer idContrato) {
        return contratoRepository.RelatorioContratoCliente(idContrato);
    }

    public ContratoDTO findByIdContrato(int id) throws RegraDeNegocioException {
        ContratoEntity contratoEntity = objectMapper.convertValue(contratoRepository.findById(id), ContratoEntity.class);
        if (contratoEntity == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }

        return converteParaContratoDTO(contratoEntity);
    }

    public ContratoDTO converteParaContratoDTO(ContratoEntity contratoEntity) {

        ContratoDTO contratoDto = objectMapper.convertValue(contratoEntity, ContratoDTO.class);
        ImovelDTO imovelDTO = objectMapper.convertValue(contratoEntity.getImovel(), ImovelDTO.class);
        imovelDTO.setDono(clienteService.findByIdClienteDto(contratoEntity.getImovel().getIdDono()));
        contratoDto.setLocatario(clienteService.findByIdClienteDto(contratoEntity.getIdLocatario()));
        contratoDto.setLocador(objectMapper.convertValue(contratoEntity.getLocador(), ClienteDTO.class));
        contratoDto.setImovel(imovelDTO);
        return contratoDto;

    }
}