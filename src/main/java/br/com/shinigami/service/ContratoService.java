package br.com.shinigami.service;


import br.com.shinigami.dto.cliente.ClienteDTO;
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
    private final ClienteService clienteService;

    @Override
    public ContratoDTO create(ContratoCreateDTO contrato) throws RegraDeNegocioException {
        Contrato contratoNovo = objectMapper.convertValue(contrato, Contrato.class);
        Imovel imovel = imovelService.findById(contrato.getIdImovel());
        contratoNovo.setAtivo(Tipo.S);
        contratoNovo.setImovel(imovel);
        contratoNovo.setLocador(clienteService.findById(imovel.getIdDono()));
        contratoNovo.setLocatario(clienteService.findById(contrato.getIdLocatario()));
        Contrato contratoAdicionado = contratoRepository.save(contratoNovo);

        ContratoDTO contratoDto = objectMapper.convertValue(contratoAdicionado, ContratoDTO.class);
        ImovelDTO imovelDTO = imovelService.findByIdImovel(contratoAdicionado.getIdImovel());
        contratoDto.setLocador(imovelDTO.getDono());
        contratoDto.setLocatario(clienteService.findByIdClienteDto(contratoAdicionado.getIdLocatario()));
        contratoDto.setImovel(imovelDTO);
        ClienteDTO locador = imovelDTO.getDono();
        ClienteDTO locatario = contratoDto.getLocatario();

        String emailBase = "Contrato criado com sucesso! <br> Contrato entre: " +
                "<br> locador: " + locador.getNome() +
                "<br> locatario: " + locatario.getNome() +
                "<br>" + "Valor Mensal: R$" + imovelDTO.getValorMensal();

        String assunto = "Seu contrato foi gerado com sucesso!!";

        emailService.sendEmail(locador,emailBase,assunto);
        emailService.sendEmail(locatario,emailBase,assunto);
        return contratoDto;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        Contrato contrato = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contrato == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        contratoRepository.delete(contrato);
    }

    @Override
    public ContratoDTO update(Integer id, ContratoCreateDTO contratoAtualizar) throws RegraDeNegocioException {
        Contrato contrato = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contrato == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        Contrato contratoEntity = objectMapper.convertValue(contratoAtualizar,Contrato.class);
        contratoEntity.setIdLocador(imovelService.findByIdImovel(contratoAtualizar.getIdImovel()).getDono().getIdCliente());

        ContratoDTO contratoDTO = objectMapper.convertValue(contratoRepository.save(contratoEntity), ContratoDTO.class);
        ImovelDTO imovelDTO = imovelService.findByIdImovel(contratoAtualizar.getIdImovel());
        contratoDTO.setLocador(imovelDTO.getDono());
        contratoDTO.setLocatario(clienteService.findByIdClienteDto(contratoAtualizar.getIdLocatario()));
        contratoDTO.setImovel(imovelDTO);


        return contratoDTO;
    }


    public List<ContratoDTO> list() throws RegraDeNegocioException {
        List<Contrato> listar = contratoRepository.findAll();
        return listar.stream()
                .map(contrato -> {
                    ContratoDTO contratoDto = objectMapper.convertValue(contrato, ContratoDTO.class);
                    try {
                        contratoDto.setLocador(clienteService.findByIdClienteDto(contrato.getIdLocador()));
                        contratoDto.setLocatario(clienteService.findByIdClienteDto(contrato.getIdLocatario()));
                        contratoDto.setImovel(imovelService.findByIdImovel(contrato.getIdImovel()));
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                    return contratoDto;
                })
                .toList();
    }

    public ContratoDTO findByIdContrato(int id) throws RegraDeNegocioException {
        Contrato contrato = objectMapper.convertValue(contratoRepository.findById(id), Contrato.class);
        if (contrato == null) {
            throw new RegraDeNegocioException("Contrato não encontrado!");
        }
        ContratoDTO contratoDto = objectMapper.convertValue(contrato, ContratoDTO.class);
        contratoDto.setLocador(clienteService.findByIdClienteDto(contrato.getIdLocador()));
        contratoDto.setLocatario(clienteService.findByIdClienteDto(contrato.getIdLocatario()));
        contratoDto.setImovel(imovelService.findByIdImovel(contrato.getIdImovel()));
        return contratoDto;
    }
}