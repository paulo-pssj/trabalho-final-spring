package br.com.shinigami.service;

import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.page.PageDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

//    @Test
//    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
//        //SETUP
//        ClienteEntity clienteEntity = getClienteEntity();
//        ClienteEntity locador = getClienteLocador();
//        ClienteEntity locatario = getClienteLocatario();
//        ClienteDTO clienteDTO = getClienteDTO();
//
//
//        EnderecoEntity enderecoEntity = getEnderecoEntity();
//        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, enderecoEntity);
//        ContratoEntity contratoEntity = getContratoEntity(locador, locatario, imovelEntity);
//        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();
//
//
//        when(contratoRepository.save(any())).thenReturn(contratoEntity);
//        when(imovelService.findById(anyInt())).thenReturn(imovelEntity);
//        when(clienteService.findById(anyInt())).thenReturn(clienteEntity);
//        when(clienteService.findByIdClienteDto(anyInt())).thenReturn(clienteDTO);
//
//
//        //ACT
//        ContratoDTO contratoDTO = new ContratoDTO();
//        contratoDTO = contratoService.create(contratoCreateDTO);
//        contratoDTO.setDataEntrada(LocalDate.of(2022, 8, 12));
//        contratoDTO.setDataVencimento(LocalDate.of(2023, 3, 10));
//
//        //ASSERT
//        assertNotNull(contratoDTO);
//        assertNotNull(contratoDTO.getIdContrato());
//        assertNotNull(contratoDTO.getLocatario());
//        assertNotNull(contratoDTO.getLocador());
//        assertNotNull(contratoDTO.getImovel());
//        assertNotNull(contratoDTO.getDataEntrada());
//        assertNotNull(contratoDTO.getDataVencimento());
//        assertNotNull(contratoDTO.getValorAluguel());
//
//
//    }


    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        //SETUP


        ClienteDTO clientoDTO = objectMapper.convertValue(getClienteLocador(), ClienteDTO.class);


        Integer idContrato = 1;

        ContratoEntity contratoEntity = getContratoEntityPronto();

        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();

        ContratoDTO contratoDTO = objectMapper.convertValue(contratoEntity, ContratoDTO.class);

        when(contratoRepository.findById(anyInt())).thenReturn(Optional.of(contratoEntity));
        when(clienteService.findByIdClienteDto(anyInt())).thenReturn(clientoDTO);
        when(imovelService.findById(anyInt())).thenReturn(contratoEntity.getImovel());

        //ACT
        ContratoDTO retornoContratoDTO = contratoService.update(idContrato, contratoCreateDTO);

        //ASSERT
        assertNotNull(retornoContratoDTO);
        assertNotEquals(retornoContratoDTO, contratoDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdateComException() throws RegraDeNegocioException {

        Integer id = 7;
        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();

        when(contratoRepository.findById(anyInt())).thenReturn(null);


        contratoService.update(id, contratoCreateDTO);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 8;

        ContratoEntity contratoEntity = getContratoEntityPronto();
        contratoEntity.setIdContrato(id);

        ClienteEntity clienteEntity = getClienteEntity();

        EnderecoEntity enderecoEntity = getEnderecoEntity();

        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, enderecoEntity);
        imovelEntity.setIdImovel(1);

        when(contratoRepository.save(any())).thenReturn(contratoEntity);

        contratoService.delete(id);

        assertNotEquals(contratoEntity, Tipo.N);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeleteComException() throws RegraDeNegocioException {

        Integer id = 7;
        ContratoCreateDTO contratoCreateDTO = getContratoCreateDTO();

        when(contratoRepository.findById(anyInt())).thenReturn(null);

        contratoService.delete(id);
    }

    @Test
    public void deveTestarListarComSucesso() throws RegraDeNegocioException {

        Integer page = 1;

        ClienteEntity clienteEntity = getClienteLocador();

        ImovelEntity imovelAntigo = new ImovelEntity(1, 2, 1, 2000, 100, TipoImovel.APARTAMENTO, Tipo.S,
                Tipo.S, Tipo.S, Tipo.N, Tipo.N, 1, Tipo.S, 1, 1, getClienteLocador(), getEnderecoEntity());


        ContratoEntity contratoEntity = getContratoEntityPronto();
        Page<ContratoEntity> paginaMock = new PageImpl<>(List.of(contratoEntity));

        List<ContratoEntity> lista = new ArrayList<>();
        lista.add(contratoEntity);

        when(contratoRepository.findAllByAtivo(any(), any(Pageable.class))).thenReturn(paginaMock);

        PageDTO<ContratoDTO> paginaMockRetorno = contratoService.list(page);

        assertNotNull(paginaMockRetorno);
    }

    @Test
    public void deveTestarRelatorioContratoClienteComSucesso() {

        ContratoEntity contratoEntity = getContratoEntityPronto();

        List<RelatorioContratoClienteDTO> relatorioDTO = new ArrayList<>();

        RelatorioContratoClienteDTO relatorioContratoClienteDTO = new RelatorioContratoClienteDTO(contratoEntity.getIdContrato(), contratoEntity.getDataEntrada(), contratoEntity.getDataVencimento()
                , contratoEntity.getValorAluguel(), contratoEntity.getLocador().getNome(), contratoEntity.getLocador().getCpf(),
                contratoEntity.getLocador().getEmail(), contratoEntity.getLocador().getTipoCliente(),
                contratoEntity.getLocatario().getNome(), contratoEntity.getLocatario().getCpf(), contratoEntity.getLocatario().getEmail(),
                contratoEntity.getLocatario().getTipoCliente());
        relatorioDTO.add(relatorioContratoClienteDTO);

        when(contratoRepository.RelatorioContratoCliente(any())).thenReturn(relatorioDTO);

        List<RelatorioContratoClienteDTO> relatorio = contratoService.relatorioContratoCliente(contratoEntity.getIdContrato());

        assertNotNull(relatorio);
        assertEquals(relatorioDTO, relatorio);

    }

//    @Test
//    public void deveTestarFindByIdContratoComSucesso() throws RegraDeNegocioException {
//        Integer id = 1;
//
//        ContratoEntity contratoEntity = getContratoEntityPronto();
//        contratoEntity.getImovel().setIdDono(1);
//
//        ImovelEntity imovelEntity = contratoEntity.getImovel();
//        imovelEntity.setIdDono(1);
//        contratoEntity.setImovel(imovelEntity);
//
//        ClienteDTO clienteDTO = getClienteDTO();
//        clienteDTO.setIdCliente(2);
//        imovelEntity.setCliente(contratoEntity.getLocador());
//
//        when(contratoRepository.findById(anyInt())).thenReturn(Optional.of(contratoEntity));
//        when(clienteService.findByIdClienteDto(anyInt())).thenReturn(clienteDTO);
//
//        ContratoDTO contratoRetorno = contratoService.findByIdContrato(id);
//
//        contratoRetorno.getImovel().getDono().setIdCliente(1);
//        assertNotNull(contratoRetorno);
//    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdContratoComException() throws RegraDeNegocioException {
        Integer id = 1;
        when(contratoRepository.findById(anyInt())).thenReturn(null);

        contratoService.findByIdContrato(id);

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
        contratoEntity.setIdLocador(1);
        contratoEntity.setIdLocatario(2);
        contratoEntity.getImovel().setIdImovel(1);
        contratoEntity.getImovel().setIdDono(1);
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
        clienteEntity.setEmail("clientelocador@mail.com");
        clienteEntity.setIdCliente(2);
        clienteEntity.setNome("Locador");
        return clienteEntity;
    }

    private ClienteEntity getClienteLocatario() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("clientelocatario@mail.com");
        clienteEntity.setIdCliente(3);
        clienteEntity.setNome("Locatario");
        return clienteEntity;
    }

    private ContratoEntity getContratoEntityPronto() {

        EnderecoEntity enderecoEntity = getEnderecoEntity();

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("cliente@mail.com");
        clienteEntity.setIdCliente(3);
        clienteEntity.setNome("Locatario");

        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, enderecoEntity);

        ContratoEntity contratoEntity = new ContratoEntity();
        contratoEntity.setAtivo(Tipo.S);
        contratoEntity.setImovel(imovelEntity);
        contratoEntity.setDataEntrada(LocalDate.now());
        contratoEntity.setDataVencimento(LocalDate.now().plusDays(30));
        contratoEntity.setLocador(getClienteLocador());
        contratoEntity.setLocatario(getClienteLocatario());
        contratoEntity.setIdContrato(1);
        contratoEntity.setValorAluguel(1000);
        contratoEntity.setIdLocador(contratoEntity.getIdLocador());
        contratoEntity.setIdLocatario(contratoEntity.getIdLocatario());
        contratoEntity.getImovel().setIdDono(clienteEntity.getIdCliente());


        return contratoEntity;
    }

    private ContratoDTO getContratoDTOPronto() {

        ContratoDTO contratoDTO = objectMapper.convertValue(getContratoEntityPronto(), ContratoDTO.class);

        return contratoDTO;
    }
}
