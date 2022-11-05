package br.com.shinigami.service;


import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
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
public class EnderecoService implements ServiceInterface<EnderecoDTO,EnderecoCreateDTO> {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EnderecoDTO create(EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        Endereco enderecoCriado = enderecoRepository.save(objectMapper.convertValue(endereco, Endereco.class));
        return objectMapper.convertValue(enderecoCriado, EnderecoDTO.class);
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        Endereco endereco = findById(id);
        if(endereco == null){
            throw new RegraDeNegocioException("Endereco não encontrado!");
        }
        enderecoRepository.delete(endereco);
    }

    @Override
    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        Endereco endereco = enderecoRepository.save(objectMapper.convertValue(enderecoAtualizar,Endereco.class));
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    @Override
    public List<EnderecoDTO> list() throws RegraDeNegocioException {
        List<Endereco> listar = enderecoRepository.findAll();
        return listar.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .toList();
    }

    public Endereco findById(Integer idEndereco) throws  RegraDeNegocioException {
        return objectMapper.convertValue(enderecoRepository.findById(idEndereco), Endereco.class);
    }

    public EnderecoDTO findByIdDto(Integer idEndereco) throws  RegraDeNegocioException {
        return objectMapper.convertValue(enderecoRepository.findById(idEndereco), EnderecoDTO.class);
    }

}
