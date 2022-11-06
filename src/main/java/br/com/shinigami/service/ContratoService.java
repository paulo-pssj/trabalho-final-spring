package br.com.shinigami.service;


import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Contrato;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
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
        Contrato contratoNovo = objectMapper.convertValue(contrato, Contrato.class);
        Imovel imovel = imovelService.findById(contrato.getIdImovel());
        contratoNovo.setAtivo(Tipo.S);
        contratoNovo.setImovel(imovel);
        contratoNovo.setLocador(clienteService.findById(imovel.getIdDono()));
        contratoNovo.setLocatario(clienteService.findById(contrato.getIdLocatario()));

        imovelService.alugarImovel(imovel);
        Contrato contratoAdicionado = contratoRepository.save(contratoNovo);

        ContratoDTO contratoDTO = converteParaContratoDTO(contratoAdicionado);

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
        Contrato contrato = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contrato == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contrato.setAtivo(Tipo.N);
        imovelService.alugarImovel(imovelService.findById(contrato.getIdImovel()));
        contratoRepository.save(contrato);
    }

    @Override
    public ContratoDTO update(Integer id, ContratoCreateDTO contratoAtualizar) throws RegraDeNegocioException {
        Contrato contratoBusca = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contratoBusca == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        Imovel imovelAntigo = imovelService.findById(contratoBusca.getIdImovel());
        imovelService.alugarImovel(imovelAntigo);
        Imovel imovelNovo = imovelService.findById(contratoAtualizar.getIdImovel());
        imovelService.alugarImovel(imovelNovo);

        Contrato contratoEntity = objectMapper.convertValue(contratoBusca, Contrato.class);
        contratoEntity.setImovel(imovelNovo);
        contratoEntity.setLocatario(clienteService.findById(contratoAtualizar.getIdLocatario()));
        contratoEntity.setDataEntrada(contratoEntity.getDataEntrada());
        contratoEntity.setDataVencimento(contratoAtualizar.getDataVencimento());
        contratoRepository.save(contratoEntity);
        return converteParaContratoDTO(contratoEntity);
    }


    public PageDTO<ContratoDTO> list(Integer page) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<Contrato>  pageContrato = contratoRepository.findAllByAtivo(Tipo.S, pageRequest);
        List<ContratoDTO> listar = pageContrato.getContent()
                .stream()
                .map(contrato -> converteParaContratoDTO(contrato))
                .toList();

        return new PageDTO<>(pageContrato.getTotalElements(),
                             pageContrato.getTotalPages(),
                             page, pageRequest.getPageSize(),
                             listar);
    }

    public List<RelatorioContratoClienteDTO> relatorioContratoCliente(Integer idContrato){
        return contratoRepository.RelatorioContratoCliente(idContrato);
    }

    public ContratoDTO findByIdContrato(int id) throws RegraDeNegocioException {
        Contrato contrato = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contrato == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }

        return converteParaContratoDTO(contrato);
    }

    public ContratoDTO converteParaContratoDTO(Contrato contrato) {

        ContratoDTO contratoDto = objectMapper.convertValue(contrato, ContratoDTO.class);
        ImovelDTO imovelDTO = objectMapper.convertValue(contrato.getImovel(), ImovelDTO.class);
        imovelDTO.setDono(clienteService.findByIdClienteDto(contrato.getImovel().getIdDono()));
        contratoDto.setLocatario(clienteService.findByIdClienteDto(contrato.getIdLocatario()));
        contratoDto.setLocador(objectMapper.convertValue(contrato.getLocador(), ClienteDTO.class));
        contratoDto.setImovel(imovelDTO);
        return contratoDto;

    }
}