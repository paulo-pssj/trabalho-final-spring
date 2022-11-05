package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteService implements ServiceInterface<ClienteDTO, ClienteCreateDTO> {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Override
    public ClienteDTO create(ClienteCreateDTO pessoa) throws RegraDeNegocioException {
        Cliente clienteNovo = objectMapper.convertValue(pessoa, Cliente.class);
        clienteNovo.setAtivo(Tipo.S);
        Cliente pessoaAdicionada = clienteRepository.save(clienteNovo);
        ClienteDTO clienteDto = objectMapper.convertValue(pessoaAdicionada, ClienteDTO.class);
        String emailBase = "Parabéns, Seu cadastro foi concluido com sucesso! Seu id é: " + clienteDto.getIdCliente();
        String assunto = "Seu cadastro foi concluido com sucesso!";
        emailService.sendEmail(clienteDto, emailBase, assunto);
        return clienteDto;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        Cliente clienteRecovery = objectMapper.convertValue(clienteRepository.findById(id), Cliente.class);
        if (clienteRecovery == null) {
            throw new RegraDeNegocioException("Cliente não encontrado!");

        }
        clienteRecovery.setAtivo(Tipo.N);
    }

    @Override
    public ClienteDTO update(Integer id, ClienteCreateDTO clienteUpdate) throws RegraDeNegocioException {
        Cliente clienteRecovery = objectMapper.convertValue(clienteRepository.findById(id), Cliente.class);
        if (clienteRecovery == null) {
            throw new RegraDeNegocioException("Cliente não encontrado!");
        }
        Cliente cliente = clienteRepository.save(objectMapper.convertValue(clienteUpdate, Cliente.class));
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> list() throws RegraDeNegocioException {
        List<Cliente> listar = clienteRepository.findAll();
        return listar.stream()
                .filter(cliente -> cliente.getAtivo().equals("S"))
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();
    }

    public ClienteDTO buscarCliente(Integer idCliente) throws RegraDeNegocioException {
        return objectMapper.convertValue(clienteRepository.findById(idCliente), ClienteDTO.class);
    }

}