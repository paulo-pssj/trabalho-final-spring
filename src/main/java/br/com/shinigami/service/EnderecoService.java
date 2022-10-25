package br.com.shinigami.service;


import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO create(EnderecoCreateDTO endereco) throws RegraDeNegocioException, BancoDeDadosException {
        Endereco enderecoNovo = objectMapper.convertValue(endereco, Endereco.class);
        Endereco enderecoCriado = enderecoRepository.create(enderecoNovo);
        log.info("Endereco criado com sucesso!");
        return objectMapper.convertValue(enderecoCriado, EnderecoDTO.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
            if(enderecoRepository.buscarEndereco(id)==null){
                throw new RegraDeNegocioException("Endereco não encontrado!");
            }
            enderecoRepository.delete(id);
    }


    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        Endereco enderecoDTO = enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(enderecoAtualizar))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereco não encontrado"));
        enderecoDTO.setCep(enderecoAtualizar.getCep());
        enderecoDTO.setCidade(enderecoAtualizar.getCidade());
        enderecoDTO.setComplemento(enderecoAtualizar.getComplemento());
        enderecoDTO.setEstado(enderecoAtualizar.getEstado());
        enderecoDTO.setPais(enderecoAtualizar.getPais());
        enderecoDTO.setRua(enderecoAtualizar.getRua());
        enderecoDTO.setNumero(enderecoAtualizar.getNumero());

        log.info("Endereço atualizado com sucesso!");
        return objectMapper.convertValue(enderecoDTO, EnderecoDTO.class);
    }


    public List<EnderecoDTO> list() throws BancoDeDadosException {
        List<Endereco> listar = enderecoRepository.list();
        return listar.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .toList();
    }

    public EnderecoDTO findById(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        return list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrada"));
    }

}
