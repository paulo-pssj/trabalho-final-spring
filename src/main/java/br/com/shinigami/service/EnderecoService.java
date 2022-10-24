package br.com.shinigami.service;


import br.com.shinigami.dto.Contrato.ContratoDTO;
import br.com.shinigami.dto.Endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.Endereco.EnderecoDTO;
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

    public void adicionar(EnderecoCreateDTO endereco) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando Endereco...");
        enderecoRepository.adicionar(objectMapper.convertValue(endereco, Endereco.class));
        System.out.println("Endereco adicinado com sucesso!");
    }

    public void remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
            if(enderecoRepository.buscarEndereco(id)==null){
                throw new RegraDeNegocioException("Endereco não encontrado!");
            }
            enderecoRepository.remover(id);
    }


    public void editar(Integer id, Endereco endereco) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            boolean conseguiuEditar = enderecoRepository.editar(id, endereco);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


    public List<EnderecoDTO> list() throws BancoDeDadosException {
        List<Endereco> listar = enderecoRepository.listar();
        return listar.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .toList();
    }
}
