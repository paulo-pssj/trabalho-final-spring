package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.entity.ClienteEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoCliente;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ClienteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private EmailService emailService;

    @Mock
    private LogService logService;
    @Mock
    private ClienteRepository clienteRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {

        // SETUP
        ClienteCreateDTO clienteCreateDTO = getClienteCreateDTO();

        ClienteEntity clienteEntity = getClienteEntity();

        ClienteDTO clienteDTO = getClienteDTO();

        clienteEntity.setIdCliente(5);
        when(clienteRepository.save(any())).thenReturn(clienteEntity);

        // Ação (ACT)
        ClienteDTO clienteDTORetorno = clienteService.create(clienteCreateDTO);

        // ASSERT
        assertNotNull(clienteDTORetorno);
        assertNotNull(clienteDTORetorno.getIdCliente());
        assertEquals("maicon@hotmail.com", clienteDTORetorno.getEmail());

    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 5;

        ClienteEntity clienteEntity1 = getClienteEntity();
        clienteEntity1.setIdCliente(5);
        clienteEntity1.setAtivo(Tipo.S);
        when(clienteRepository.findByIdClienteAndAtivo(anyInt(), any(Tipo.class))).thenReturn((clienteEntity1));
        when(clienteRepository.save(any())).thenReturn(clienteEntity1);


        // Ação (ACT)
        clienteService.delete(id);

        // Verificação (ASSERT)
        verify(clienteRepository, times(1)).save(any());

    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 5;
        ClienteCreateDTO clienteCreateDTO = getClienteCreateDTO();

        // findById(id);
        ClienteEntity clienteEntity1 = getClienteEntity();
        clienteEntity1.setNome("Pablo Horácio Guiñazu");
        clienteEntity1.setIdCliente(5);
        clienteEntity1.setAtivo(Tipo.S);
        when(clienteRepository.findByIdClienteAndAtivo(anyInt(), any(Tipo.class))).thenReturn((clienteEntity1));
        when(clienteRepository.save(any())).thenReturn(clienteEntity1);

        // pessoaRepository.save(pessoaEntityRecuperada);
        ClienteEntity clienteEntity = getClienteEntity();
        when(clienteRepository.save(any())).thenReturn(clienteEntity);

        // ACT
        ClienteDTO clienteDTO = clienteService.update(id, clienteCreateDTO);

        // ASSERT
        assertNotNull(clienteDTO);
        assertNotNull(clienteEntity);
        assertNotEquals("Pablo Horácio Guiñazu", clienteDTO.getNome());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 5;

        //pessoaRepository.findById(id)
        ClienteEntity clienteEntity1 = getClienteEntity();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(clienteEntity1));

        // Ação (ACT)
        ClienteEntity clienteEntity = clienteService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(clienteEntity);

    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        List<ClienteEntity> lista = new ArrayList<>();
        lista.add(getClienteEntity());
        when(clienteRepository.findAllByAtivo(any())).thenReturn(lista);

        // Ação (ACT)
        List<ClienteDTO> list = clienteService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarListByLocadorLocatarioComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        List<ClienteEntity> listar = new ArrayList<>();
        listar.add(getClienteEntity());
        when(clienteRepository.findByTipoClienteAndAtivo(any(TipoCliente.class), any(Tipo.class))).thenReturn(listar);


        // Ação (ACT)
        List<ClienteDTO> lista = clienteService.listByLocadorLocatario(TipoCliente.LOCADOR);


        // Verificação (ASSERT)
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarFindById() throws RegraDeNegocioException {
        Integer idCliente = 5;

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());

        clienteService.findById(idCliente);

        verify(clienteRepository, times(1)).findById(any());
    }

    @Test
    public void deveTestarFindByIdClienteDto() {
        Integer idCliente = 5;

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());

        clienteService.findByIdClienteDto(idCliente);
        verify(clienteRepository, times(1)).findById(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdateComException() throws RegraDeNegocioException {

        ClienteCreateDTO clienteCreateDTO = getClienteCreateDTO();

        when(clienteRepository.findByIdClienteAndAtivo(any(), any())).thenReturn(null);

        clienteService.update(1, clienteCreateDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeleteComException() throws RegraDeNegocioException {

        when(clienteRepository.findByIdClienteAndAtivo(any(), any())).thenReturn(null);

        clienteService.delete(1);

    }


    private static ClienteDTO getClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(5);
        clienteDTO.setNome("Maicon");
        clienteDTO.setCpf("12345678910");
        clienteDTO.setEmail("maicon@hotmail.com");
        return clienteDTO;
    }

    private static ClienteEntity getClienteEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Maicon");
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("maicon@hotmail.com");
        clienteEntity.setTelefone("4891654568");
        clienteEntity.setTipoCliente(TipoCliente.LOCADOR);
        return clienteEntity;
    }


    private static ClienteCreateDTO getClienteCreateDTO() {
        ClienteCreateDTO clienteCreateDTO = new ClienteCreateDTO();
        clienteCreateDTO.setNome("Maicon");
        clienteCreateDTO.setCpf("12345678910");
        clienteCreateDTO.setEmail("maicon@hotmail.com");
        clienteCreateDTO.setTelefone("4891654568");
        return clienteCreateDTO;
    }

}
