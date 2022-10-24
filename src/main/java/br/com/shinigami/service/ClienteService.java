package br.com.shinigami.service;

import br.com.shinigami.dto.Cliente.ClienteCreateDTO;
import br.com.shinigami.dto.Cliente.ClienteDTO;
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



    public ClienteDTO create(ClienteCreateDTO pessoa) throws RegraDeNegocioException,BancoDeDadosException {
            Cliente clienteNovo = objectMapper.convertValue(pessoa, Cliente.class);
            log.info("criando cliente...");
            Cliente pessoaAdicionada = clienteRepository.adicionar(clienteNovo);
            log.info("Cliente criado com sucesso!");
            return objectMapper.convertValue(pessoaAdicionada, ClienteDTO.class);
    }


    public void delete(Integer id) throws RegraDeNegocioException,BancoDeDadosException {
            log.info("Deletando cliente...");
            Cliente clienteRecovery = clienteRepository.listar().stream()
                    .filter(cliente -> cliente.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
            clienteRepository.listar().remove(clienteRecovery);
            log.info("Cliente deletado com sucesso!");
    }


    public ClienteDTO update(Integer id, ClienteCreateDTO clienteUpdate) throws RegraDeNegocioException,BancoDeDadosException {
        log.info("Atualizando cliente...");
        Cliente clienteRecovery = clienteRepository.listar().stream()
                .filter(cliente -> cliente.getIdCliente().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
        clienteRecovery.setNome(clienteUpdate.getNome());
        clienteRecovery.setCpf(clienteUpdate.getCpf());
        clienteRecovery.setEmail(clienteUpdate.getEmail());
        log.info("Cliente atualizado com sucesso!");
        return objectMapper.convertValue(clienteRecovery, ClienteDTO.class);
    }


//    public void listar() throws BancoDeDadosException {
//            List<Cliente> listar = clienteRepository.listar();
//            listar.forEach(cliente -> {
//                System.out.println("id:" + cliente.getIdCliente() + " | Nome: " + cliente.getNome());
//            });
//    }

    public Cliente buscarCliente(String busca) throws RegraDeNegocioException,BancoDeDadosException {
            int id = Integer.parseInt(busca.trim());
            return clienteRepository.buscarCliente(id);
    }
//
//    public boolean validaCpf(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getCpf().length() != 11) {
//            throw new DadoInvalidoException("CPF Invalido!");
//        }
//        return true;
//    }
//
//    public boolean validaNome(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getNome().equals(null) || cliente.getNome().isBlank()) {
//            throw new DadoInvalidoException("Nome Invalido!");
//        }
//        return true;
//    }
//
//    public boolean validaEmail(Cliente cliente) throws DadoInvalidoException {
//        if (cliente.getEmail().isBlank() || !cliente.getEmail().contains("@")) {
//            throw new DadoInvalidoException("Email invalido!");
//        }
//        return true;
//    }


}