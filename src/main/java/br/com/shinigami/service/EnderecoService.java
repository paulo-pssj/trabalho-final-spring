package br.com.shinigami.service;


import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.Tipo;
import br.com.shinigami.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoService implements ServiceInterface<EnderecoDTO, EnderecoCreateDTO> {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EnderecoDTO create(EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityCriado = objectMapper.convertValue(endereco, EnderecoEntity.class);
        enderecoEntityCriado.setAtivo(Tipo.S);
        enderecoRepository.save(enderecoEntityCriado);
        return objectMapper.convertValue(enderecoEntityCriado, EnderecoDTO.class);
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = objectMapper.convertValue(enderecoRepository.findById(id), EnderecoEntity.class);
        if (enderecoEntity == null) {
            throw new RegraDeNegocioException("Endereco não encontrado!");
        }
        enderecoEntity.setAtivo(Tipo.N);
        enderecoRepository.save(enderecoEntity);
    }

    @Override
    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
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
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
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
