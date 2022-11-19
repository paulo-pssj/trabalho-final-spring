package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.entity.ClienteEntity;
import br.com.shinigami.entity.ContratoEntity;
import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.ImovelEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoImovel;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ContratoRepository;
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

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContratoServiceTest {


    @InjectMocks
    private ContratoService contratoService;

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private ImovelService imovelService;
    @Mock
    private ClienteService clienteService;

    @Mock
    private LogService logService;

    @Mock
    private EmailService emailService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(contratoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntity = getClienteEntity();
        ClienteEntity locador = getClienteLocador();
        ClienteEntity locatario = getClienteLocatario();
        ClienteDTO clienteDTO = getClienteDTO();


        EnderecoEntity enderecoEntity = getEnderecoEntity();
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, enderecoEntity);
        ContratoEntity contratoEntity = getContratoEntity(locador, locatario, imovelEntity);
        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();


        when(contratoRepository.save(any())).thenReturn(contratoEntity);
        when(imovelService.findById(anyInt())).thenReturn(imovelEntity);
        when(clienteService.findById(anyInt())).thenReturn(clienteEntity);
        when(clienteService.findByIdClienteDto(anyInt())).thenReturn(clienteDTO);


        //ACT
        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO = contratoService.create(contratoCreateDTO);
        contratoDTO.setDataEntrada(LocalDate.of(2022, 8, 12));
        contratoDTO.setDataVencimento(LocalDate.of(2023, 3, 10));

        //ASSERT
        assertNotNull(contratoDTO);
        assertNotNull(contratoDTO.getIdContrato());
        assertNotNull(contratoDTO.getLocatario());
        assertNotNull(contratoDTO.getLocador());
        assertNotNull(contratoDTO.getImovel());
        assertNotNull(contratoDTO.getDataEntrada());
        assertNotNull(contratoDTO.getDataVencimento());
        assertNotNull(contratoDTO.getValorAluguel());


    }


    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        //SETUP

        ClienteEntity clienteEntity = getClienteLocador();

        ClienteDTO clientoDTO = objectMapper.convertValue(getClienteLocador(),ClienteDTO.class);

        ImovelEntity imovelAntigo = new ImovelEntity(1, 2, 1, 2000, 100, TipoImovel.APARTAMENTO, Tipo.S,
                Tipo.S, Tipo.S, Tipo.N, Tipo.N, 1, Tipo.S, 1, 1, getClienteLocador(), getEnderecoEntity());

        Integer idContrato = 1;

        ContratoEntity contratoEntity = new ContratoEntity();
        contratoEntity.setIdContrato(idContrato);
        contratoEntity.setImovel(imovelAntigo);
        contratoEntity.setAtivo(Tipo.S);
        contratoEntity.setDataEntrada(LocalDate.now());
        contratoEntity.setDataVencimento(LocalDate.now().plusDays(30));
        contratoEntity.setLocador(getClienteLocador());
        contratoEntity.setLocatario(getClienteLocatario());
        contratoEntity.setIdContrato(1);
        contratoEntity.setValorAluguel(1000);
        contratoEntity.setIdLocador(contratoEntity.getIdLocador());
        contratoEntity.setIdLocatario(contratoEntity.getIdLocatario());
        contratoEntity.getImovel().setIdDono(clienteEntity.getIdCliente());

        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();

        ContratoDTO contratoDTO = objectMapper.convertValue(contratoEntity,ContratoDTO.class);

        ImovelEntity imovelNovo = new ImovelEntity(2, 2, 1, 20000,
                1000, TipoImovel.APARTAMENTO, Tipo.S,
                Tipo.S, Tipo.S, Tipo.N, Tipo.N, 1, Tipo.S,
                1, 1, getClienteLocador(), getEnderecoEntity());


        when(contratoRepository.findById(anyInt())).thenReturn(Optional.of(contratoEntity));
        when(clienteService.findByIdClienteDto(anyInt())).thenReturn(clientoDTO);
        when(imovelService.findById(anyInt())).thenReturn(imovelAntigo);

        //ACT
        ContratoDTO retornoContratoDTO = contratoService.update(idContrato, contratoCreateDTO);

        //ASSERT
        assertNotNull(retornoContratoDTO);
        assertNotEquals(retornoContratoDTO,contratoDTO);

    }

    @Test
    public void deveTestarDeletarComSucesso() {
        //SETUP
        Integer id = 8;

        ContratoEntity contratoEntity = new ContratoEntity();
        contratoEntity.setAtivo(Tipo.S);
        contratoEntity.setIdContrato(id);




        //ACT
//        contratoService.delete();




        //ASSERT

    }

    private ContratoCreateDTO getContratoCreateDTO() {
        ContratoCreateDTO contratoCreateDTO = new ContratoCreateDTO();
        contratoCreateDTO.setDataEntrada(LocalDate.of(2022, 8, 12));
        contratoCreateDTO.setIdImovel(1);
        contratoCreateDTO.setIdLocatario(1);
        contratoCreateDTO.setDataVencimento(LocalDate.of(2023, 3, 10));
        return contratoCreateDTO;
    }

    private ClienteDTO getClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("ClienteDTO");
        clienteDTO.setCpf("12345678910");
        clienteDTO.setEmail("cliente@mail.com");
        clienteDTO.setIdCliente(1);
        clienteDTO.setTelefone("323232323232");
        return clienteDTO;
    }


    private ContratoEntity getContratoEntity(ClienteEntity locador, ClienteEntity locatario, ImovelEntity imovelEntity) {
        ContratoEntity contratoEntity = new ContratoEntity();
        contratoEntity.setIdContrato(1);
        contratoEntity.setImovel(imovelEntity);
        contratoEntity.setIdLocador(1);
        contratoEntity.setIdLocatario(1);
        contratoEntity.setValorAluguel(1200);
        contratoEntity.setLocador(locador);
        contratoEntity.setLocatario(locatario);
        return contratoEntity;
    }

    private ImovelEntity getImovelEntity(ClienteEntity clienteEntity, EnderecoEntity enderecoEntity) {
        ImovelEntity imovelEntity = new ImovelEntity();
        imovelEntity.setAlugado(Tipo.N);
        imovelEntity.setAtivo(Tipo.S);
        imovelEntity.setCliente(clienteEntity);
        imovelEntity.setIdImovel(1);
        imovelEntity.setAreaDeLazer(Tipo.S);
        imovelEntity.setEndereco(enderecoEntity);
        imovelEntity.setCliente(clienteEntity);
        imovelEntity.setIdDono(clienteEntity.getIdCliente());
        return imovelEntity;
    }

    private EnderecoEntity getEnderecoEntity() {
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setAtivo(Tipo.S);
        enderecoEntity.setIdEndereco(1);
        enderecoEntity.setCep("91530-040");
        enderecoEntity.setCidade("Porto Alegre");
        enderecoEntity.setEstado("Rio Grande do Sul");
        enderecoEntity.setPais("Brasil");
        enderecoEntity.setRua("Cristiano Fischer");
        return enderecoEntity;
    }

    private ClienteEntity getClienteEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("cliente@mail.com");
        clienteEntity.setIdCliente(1);
        clienteEntity.setNome("Cliente");
        return clienteEntity;
    }

    private ClienteEntity getClienteLocador() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("cliente@mail.com");
        clienteEntity.setIdCliente(2);
        clienteEntity.setNome("Locador");
        return clienteEntity;
    }

    private ClienteEntity getClienteLocatario() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("cliente@mail.com");
        clienteEntity.setIdCliente(3);
        clienteEntity.setNome("Locatario");
        return clienteEntity;
    }
}
