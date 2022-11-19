package br.com.shinigami.service;


import br.com.shinigami.dto.RelatorioImovelEnderecoDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.log.LogCreateDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.entity.ImovelEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ImovelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class        ImovelService implements ServiceInterface<ImovelDTO, ImovelCreateDTO> {
    private final ImovelRepository imovelRepository;
    private final EnderecoService enderecoService;
    private final ClienteService clienteService;

    private final LogService logService;
    private final ObjectMapper objectMapper;

    public PageDTO<ImovelDTO> list(Integer page) throws RegraDeNegocioException {

        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<ImovelEntity> pageImovel = imovelRepository.findAllByAtivo(Tipo.S, pageRequest);
        List<ImovelDTO> listar = pageImovel.getContent().stream()
                .map(imovel -> converteParaImovelDTO(imovel))
                .toList();
        return new PageDTO<>(pageImovel.getTotalElements(),
                pageImovel.getTotalPages(),
                page, pageRequest.getPageSize(), listar);
    }

    public ImovelDTO findByIdImovel(Integer id) throws RegraDeNegocioException {
        ImovelEntity imovel = findById(id);
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);
        return imovelDTO;
    }

    @Override
    public ImovelDTO create(ImovelCreateDTO imovelNovo) throws RegraDeNegocioException {
        ImovelEntity imovel = objectMapper.convertValue(imovelNovo, ImovelEntity.class);
        imovel.setEndereco(enderecoService.findById(imovelNovo.getIdEndereco()));
        imovel.setCliente(clienteService.findById(imovelNovo.getIdDono()));
        imovel.setAtivo(Tipo.S);
        imovelRepository.save(imovel);
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);

        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.IMOVEL,"IMOVEL CRIADO", LocalDate.now());
        logService.create(logCreateDTO);

        return imovelDTO;
    }

    @Override
    public ImovelDTO update(Integer id, ImovelCreateDTO imovelNovo) throws RegraDeNegocioException {
        ImovelEntity imovel = objectMapper.convertValue(imovelRepository.findByIdImovelAndAtivo(id, Tipo.S), ImovelEntity.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel Não Encontrado");
        }
        imovel.setEndereco(enderecoService.findById(imovelNovo.getIdEndereco()));
        imovel.setCliente(clienteService.findById(imovelNovo.getIdDono()));
        imovelRepository.save(imovel);
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);

        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.IMOVEL,"IMOVEL ATUALIZADO", LocalDate.now());
        logService.create(logCreateDTO);

        return imovelDTO;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        ImovelEntity imovel = findById(id);

        imovel.setAtivo(Tipo.N);
        imovel.setCliente(clienteService.findById(imovel.getIdDono()));
        imovelRepository.save(imovel);

        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.IMOVEL,"IMOVEL DELETADO", LocalDate.now());
        logService.create(logCreateDTO);
    }

    public List<ImovelDTO> listarImoveisDisponiveis() throws RegraDeNegocioException {
        List<ImovelEntity> listar = imovelRepository.findAllByAlugadoAndAtivo(Tipo.N, Tipo.S);
        return listar.stream()
                .map(imovel -> converteParaImovelDTO(imovel))
                .toList();
    }

    private ImovelDTO converteParaImovelDTO(ImovelEntity imovel) {

        ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);
        imovelDTO.setEndereco(objectMapper.convertValue(imovel.getEndereco(), EnderecoDTO.class));
        imovelDTO.setDono(objectMapper.convertValue(imovel.getCliente(), ClienteDTO.class));
        return imovelDTO;

    }

    public ImovelEntity findById(Integer id) throws RegraDeNegocioException {
        ImovelEntity imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Imovel não encontrado"));
 
        return objectMapper.convertValue(imovel, ImovelEntity.class);
    }

    public void alugarImovel(ImovelEntity imovel) throws RegraDeNegocioException {
        imovel.setCliente(clienteService.findById(imovel.getIdDono()));
        if (imovel.getAlugado().equals(Tipo.N)) {
            imovel.setAlugado(Tipo.S);

            LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.IMOVEL,"IMOVEL ALUGADO", LocalDate.now());
            logService.create(logCreateDTO);
        } else {
            imovel.setAlugado(Tipo.N);

            LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.IMOVEL,"IMOVEL LIBERADO", LocalDate.now());
            logService.create(logCreateDTO);
        }
        imovelRepository.save(imovel);
    }

    public List<RelatorioImovelEnderecoDTO> relatorioImovelEndereco(Integer idImovel) {
        return imovelRepository.retornarRelatorioImovelEnderecoDTO(idImovel);

    }
}

