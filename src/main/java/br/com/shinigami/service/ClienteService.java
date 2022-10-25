package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public ClienteDTO create(ClienteCreateDTO pessoa) throws RegraDeNegocioException, BancoDeDadosException {
        Cliente clienteNovo = objectMapper.convertValue(pessoa, Cliente.class);
        log.info("criando cliente...");
        Cliente pessoaAdicionada = clienteRepository.create(clienteNovo);
        log.info("Cliente criado com sucesso!");
        return objectMapper.convertValue(pessoaAdicionada, ClienteDTO.class);
    }


    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Deletando cliente...");
        Cliente clienteRecovery = clienteRepository.buscarCliente(id);
        if (clienteRecovery == null) {
            throw new RegraDeNegocioException("Cliente não encontrado!");
        }
        clienteRepository.delete(clienteRecovery.getIdCliente());
        log.info("Cliente deletado com sucesso!");
    }


    public ClienteDTO update(Integer id, ClienteCreateDTO clienteUpdate) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando cliente...");
        Cliente clienteRecovery = clienteRepository.buscarCliente(id);
        if (clienteRecovery == null) {
            throw new RegraDeNegocioException("Cliente não encontrado!");
        }
        clienteRecovery.setNome(clienteUpdate.getNome());
        clienteRecovery.setCpf(clienteUpdate.getCpf());
        clienteRecovery.setEmail(clienteUpdate.getEmail());
        log.info("Cliente atualizado com sucesso!");
        return objectMapper.convertValue(clienteRecovery, ClienteDTO.class);
    }


    public List<ClienteDTO> list() throws BancoDeDadosException {
        List<Cliente> listar = clienteRepository.list();
        return listar.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();
    }

    public ClienteDTO buscarCliente(Integer idCliente) throws RegraDeNegocioException, BancoDeDadosException {
        return objectMapper.convertValue(clienteRepository.buscarCliente(idCliente), ClienteDTO.class);
    }

}