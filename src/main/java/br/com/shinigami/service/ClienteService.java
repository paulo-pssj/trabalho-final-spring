package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.log.LogCreateDTO;
import br.com.shinigami.entity.ClienteEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoCliente;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteService implements ServiceInterface<ClienteDTO, ClienteCreateDTO> {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    private final LogService logService;

    @Override
    public ClienteDTO create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntityNovo = objectMapper.convertValue(cliente, ClienteEntity.class);
        clienteEntityNovo.setAtivo(Tipo.S);
        clienteRepository.save(clienteEntityNovo);

        ClienteDTO clienteDto = objectMapper.convertValue(clienteEntityNovo, ClienteDTO.class);
        String emailBase = "Parabéns, Seu cadastro foi concluido com sucesso! Seu id é: " + clienteDto.getIdCliente();
        String assunto = "Seu cadastro foi concluido com sucesso!";
        emailService.sendEmail(clienteDto, emailBase, assunto);

        LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.CLIENTE,"CLIENTE CRIADO", LocalDate.now());
        logService.create(logCreateDTO);

        return clienteDto;

    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            ClienteEntity cliente = objectMapper.convertValue(clienteRepository.findByIdClienteAndAtivo(id, Tipo.S), ClienteEntity.class);
            if (cliente == null) {
                throw new RegraDeNegocioException("Cliente não encontrado!");
            }
            cliente.setAtivo(Tipo.N);
            clienteRepository.save(cliente);
            LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.CLIENTE,"CLIENTE DELETADO", LocalDate.now());
            logService.create(logCreateDTO);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    @Override
    public ClienteDTO update(Integer id, ClienteCreateDTO clienteUpdate) throws RegraDeNegocioException {
        try {
            ClienteEntity clienteRecovery = objectMapper.convertValue(clienteRepository.findByIdClienteAndAtivo(id, Tipo.S), ClienteEntity.class);
            if (clienteRecovery == null) {
                throw new RegraDeNegocioException("Cliente não encontrado!");
            }
            clienteRecovery.setNome(clienteUpdate.getNome());
            clienteRecovery.setCpf(clienteUpdate.getCpf());
            clienteRecovery.setEmail(clienteUpdate.getEmail());
            clienteRecovery.setTelefone(clienteUpdate.getTelefone());
            clienteRecovery.setTipoCliente(clienteUpdate.getTipoCliente());
            ClienteEntity cliente = clienteRepository.save(clienteRecovery);

            LogCreateDTO logCreateDTO = new LogCreateDTO(TipoLog.CLIENTE,"CLIENTE ATUALIZADO", LocalDate.now());
            logService.create(logCreateDTO);

            return objectMapper.convertValue(cliente, ClienteDTO.class);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }


    public List<ClienteDTO> list() throws RegraDeNegocioException {
        List<ClienteEntity> listar = clienteRepository.findAllByAtivo(Tipo.S);
        return listar.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();

    }

    public List<ClienteDTO> listByLocadorLocataio(TipoCliente tipo) throws RegraDeNegocioException {

        List<ClienteEntity> listar = clienteRepository.findByTipoClienteAndAtivo(tipo, Tipo.S);
        return listar.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();
    }

    public ClienteDTO findByIdClienteDto(Integer idCliente) {
        return objectMapper.convertValue(clienteRepository.findById(idCliente), ClienteDTO.class);
    }

    public ClienteEntity findById(Integer idCliente) throws RegraDeNegocioException {
        return objectMapper.convertValue(clienteRepository.findById(idCliente), ClienteEntity.class);
    }
}