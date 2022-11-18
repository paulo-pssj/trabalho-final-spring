package br.com.shinigami.service;


import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.dto.log.LogCreateDTO;
import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoService implements ServiceInterface<EnderecoDTO, EnderecoCreateDTO> {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final ContextService contextService;

    private final LogService logService;

    @Override
    public EnderecoDTO create(EnderecoCreateDTO endereco) throws RegraDeNegocioException, IOException, InterruptedException, ApiException {
        EnderecoEntity enderecoEntityCriado = objectMapper.convertValue(endereco, EnderecoEntity.class);
        enderecoEntityCriado.setAtivo(Tipo.S);
        enderecoRepository.save(enderecoEntityCriado);

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntityCriado, EnderecoDTO.class);


        contextService.gerarContext(enderecoDTO);

       LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.ENDERECO,"ENDEREÇO CRIADO", LocalDate.now());
        logService.create(logCreateDTO);
        return enderecoDTO;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = objectMapper.convertValue(enderecoRepository.findById(id), EnderecoEntity.class);
        if (enderecoEntity == null) {
            throw new RegraDeNegocioException("Endereco não encontrado!");
        }
        enderecoEntity.setAtivo(Tipo.N);
        enderecoRepository.save(enderecoEntity);
        contextService.delete(id);
        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.ENDERECO,"ENDEREÇO DELETADO", LocalDate.now());
        logService.create(logCreateDTO);
    }

    @Override
    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, IOException, InterruptedException, ApiException {
        EnderecoEntity enderecoEntityRecovery = objectMapper.convertValue(enderecoRepository.findById(id), EnderecoEntity.class);
        if (enderecoEntityRecovery == null) {
            throw new RegraDeNegocioException("Endereço não encontrado!");
        }
        enderecoEntityRecovery.setRua(enderecoAtualizar.getRua());
        enderecoEntityRecovery.setCidade(enderecoAtualizar.getCidade());
        enderecoEntityRecovery.setEstado(enderecoAtualizar.getEstado());
        enderecoEntityRecovery.setPais(enderecoAtualizar.getPais());
        enderecoEntityRecovery.setCep(enderecoAtualizar.getCep());
        enderecoEntityRecovery.setComplemento(enderecoAtualizar.getComplemento());
        enderecoEntityRecovery.setNumero(enderecoAtualizar.getNumero());
        enderecoEntityRecovery.setAtivo(Tipo.S);
        EnderecoEntity enderecoEntity = enderecoRepository.save(enderecoEntityRecovery);

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);

        contextService.delete(id);
        contextService.gerarContext(enderecoDTO);
        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.ENDERECO,"ENDEREÇO ATUALIZADO", LocalDate.now());
        logService.create(logCreateDTO);
        return enderecoDTO;
    }

    public List<EnderecoDTO> list() throws RegraDeNegocioException {
        List<EnderecoEntity> listar = enderecoRepository.findAllByAtivo(Tipo.S);
        return listar.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .toList();
    }

    public EnderecoEntity findById(Integer idEndereco) throws RegraDeNegocioException {
        return objectMapper.convertValue(enderecoRepository.findById(idEndereco), EnderecoEntity.class);
    }

    public EnderecoDTO findByIdEnderecoDto(Integer idEndereco) throws RegraDeNegocioException {
        return objectMapper.convertValue(enderecoRepository.findById(idEndereco), EnderecoDTO.class);

    }

}
