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
public class EnderecoService implements ServiceInterface<EnderecoDTO,EnderecoCreateDTO> {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EnderecoDTO create(EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        try{
            Endereco enderecoCriado = enderecoRepository.create(objectMapper.convertValue(endereco, Endereco.class));
            log.info("Endereco criado com sucesso!");
            return objectMapper.convertValue(enderecoCriado, EnderecoDTO.class);
        }catch(BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro ao criar endereço");
        }
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        try{
            if(enderecoRepository.buscarEndereco(id)==null){
                throw new RegraDeNegocioException("Endereco não encontrado!");
            }
            enderecoRepository.delete(id);
        }catch (BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro ao deletar Endereço");
        }
    }

    @Override
    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        try {
            Endereco endereco = enderecoRepository.buscarEndereco(id);

            endereco.setCep(enderecoAtualizar.getCep());
            endereco.setCidade(enderecoAtualizar.getCidade());
            endereco.setComplemento(enderecoAtualizar.getComplemento());
            endereco.setEstado(enderecoAtualizar.getEstado());
            endereco.setPais(enderecoAtualizar.getPais());
            endereco.setRua(enderecoAtualizar.getRua());
            endereco.setNumero(enderecoAtualizar.getNumero());
            enderecoRepository.update(id, endereco);
            log.info("Endereço atualizado com sucesso!");
            return objectMapper.convertValue(endereco, EnderecoDTO.class);
        }catch (BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro ao atualizar endereço");
        }
    }

    @Override
    public List<EnderecoDTO> list() throws RegraDeNegocioException {
        try {
            List<Endereco> listar = enderecoRepository.list();
            return listar.stream()
                    .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                    .toList();
        }catch (BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro ao buscar endereço no banco");
        }
    }

    public EnderecoDTO findById(Integer idEndereco) throws  RegraDeNegocioException {
        try{
            return objectMapper.convertValue(enderecoRepository.buscarEndereco(idEndereco), EnderecoDTO.class);
        }catch (BancoDeDadosException bd){
            throw new RegraDeNegocioException("Endereço não encontrado!");
        }
    }

}
