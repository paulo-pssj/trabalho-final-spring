package br.com.shinigami.service;

import br.com.shinigami.dto.RelatorioImovelEnderecoDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.entity.ClienteEntity;
import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.ImovelEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoImovel;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.ImovelRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImovelServiceTest {

    @InjectMocks
    private ImovelService imovelService;

    @Mock
    private ImovelRepository imovelRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private LogService logService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(imovelService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarFindByIdImovelComSucesso() throws RegraDeNegocioException {
        //setup
        Integer idImovel = 1;
        ClienteEntity clienteLocador = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovel = getImovelEntity(clienteLocador, endereco);

        when(imovelRepository.findById(any())).thenReturn(Optional.of(imovel));
        //act
        ImovelDTO imovelDTOBusca = imovelService.findByIdImovel(idImovel);

        //assert
        assertNotNull(imovelDTOBusca);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException{
        //setup
        Integer idImovel = 1;
        ClienteEntity clienteLocador = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovel = getImovelEntity(clienteLocador, endereco);

        when(imovelRepository.findById(any())).thenReturn(Optional.of(imovel));
        //act
        ImovelEntity imovelEntity = imovelService.findById(idImovel);

        //assert
        assertNotNull(imovelEntity);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComException() throws RegraDeNegocioException{
        Integer idImovel = 10;

        when(imovelRepository.findById(anyInt())).thenReturn(Optional.empty());

        imovelService.findById(idImovel);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelCreateDTO imovelCreateDTO = getImovelCreateDTO(clienteEntity, endereco);
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);

        when(imovelRepository.save(any())).thenReturn(imovelEntity);

        ImovelDTO imovelDTO = imovelService.create(imovelCreateDTO);

        assertNotNull(imovelDTO);
    }

    @Test
    public void deveTestasUpdadeComSucesso() throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelCreateDTO imovelCreateDTO = getImovelCreateDTO(clienteEntity, endereco);
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);

        when(imovelRepository.save(any())).thenReturn(imovelEntity);
        when(imovelRepository.findByIdImovelAndAtivo(anyInt(), any())).thenReturn(imovelEntity);

        ImovelDTO imovelDTO = imovelService.update(1,  imovelCreateDTO);

        assertNotNull(imovelDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdadeComException() throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelCreateDTO imovelCreateDTO = getImovelCreateDTO(clienteEntity, endereco);

        when(imovelRepository.findByIdImovelAndAtivo(anyInt(), any())).thenReturn(null);

        imovelService.update(1,  imovelCreateDTO);

        verify(imovelRepository, times(1)).save(any());

    }

    @Test
    public void deveTestarAlugarImovelComSucessoComImovelNaoAlugado() throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);

        when(clienteService.findById(any())).thenReturn(clienteEntity);

        imovelService.alugarImovel(imovelEntity);

        verify(imovelRepository, times(1)).save(any());

    }

    @Test
    public void deveTestarAlugarImovelComSucessoComImovelAlugado() throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);

        imovelEntity.setAlugado(Tipo.S);

        when(clienteService.findById(any())).thenReturn(clienteEntity);

        imovelService.alugarImovel(imovelEntity);

        verify(imovelRepository, times(1)).save(any());

    }

    @Test
    public  void deveTestarDeleteComSucesso()throws RegraDeNegocioException{
        Integer idImovel = 1;
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovel = getImovelEntity(clienteEntity, endereco);

        when(imovelRepository.findById(anyInt())).thenReturn(Optional.of(imovel));
        when(imovelRepository.save(any())).thenReturn(imovel);

        imovelService.delete(idImovel);

        verify(imovelRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarListarImoveisDisponiveisComSucesso()throws RegraDeNegocioException{
        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);

        List<ImovelEntity> lista = new ArrayList<>();
        lista.add(imovelEntity);

        when(imovelRepository.findAllByAlugadoAndAtivo(any(), any())).thenReturn(lista);

        List<ImovelDTO> listaDTO = imovelService.listarImoveisDisponiveis();

        assertEquals(1, listaDTO.size());
    }

    @Test
    public void deveTestarListaRelatorioImovelEndereco(){
        RelatorioImovelEnderecoDTO relatorio = getRelatorio();
        List<RelatorioImovelEnderecoDTO>  lista = new ArrayList<>();
        lista.add(relatorio);

        when(imovelRepository.retornarRelatorioImovelEnderecoDTO(any())).thenReturn(lista);

        List<RelatorioImovelEnderecoDTO> listaRelatorio = imovelService.relatorioImovelEndereco(1);

        assertEquals(1, listaRelatorio.size());

    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException{
        Integer pagina = 1;
        Integer tamanho = 1;

        ClienteEntity clienteEntity = getClienteLocador();
        EnderecoEntity endereco = getEnderecoEntity();
        ImovelEntity imovelEntity = getImovelEntity(clienteEntity, endereco);
        PageImpl<ImovelEntity> page = new PageImpl<>(List.of(imovelEntity));

        when(imovelRepository.findAllByAtivo(any(), any())).thenReturn(page);

        PageDTO<ImovelDTO> listaRetorno = imovelService.list(pagina);

        assertEquals(1, listaRetorno.getQuantidadePaginas());
        assertEquals(1, listaRetorno.getTotalElementos());
    }

    private  RelatorioImovelEnderecoDTO getRelatorio() {
        RelatorioImovelEnderecoDTO relatorio = new RelatorioImovelEnderecoDTO();
        relatorio.setIdCliente(1);
        relatorio.setNome("Paulo");
        relatorio.setEmail("test@gmail.com");
        relatorio.setIdImovel(1);
        relatorio.setTipoImovel(TipoImovel.APARTAMENTO);
        relatorio.setValorMensal(2000);
        relatorio.setCidade("Cotia");
        relatorio.setEstado("São Paulo");
        relatorio.setPais("Brasil");

        return relatorio;
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

    private ImovelCreateDTO getImovelCreateDTO(ClienteEntity clienteEntity, EnderecoEntity enderecoEntity) {
        ImovelCreateDTO imovel = new ImovelCreateDTO();
        imovel.setAlugado(Tipo.N);
        imovel.setAreaDeLazer(Tipo.S);
        imovel.setIdEndereco(enderecoEntity.getIdEndereco());
        imovel.setIdDono(clienteEntity.getIdCliente());
        return imovel;
    }

    private ImovelDTO getImovelDTO(ClienteEntity clienteEntity, EnderecoEntity enderecoEntity){
        ImovelDTO imovelDTO = new ImovelDTO();
        imovelDTO.setAlugado(Tipo.N);
        imovelDTO.setIdImovel(1);
        imovelDTO.setAreaDeLazer(Tipo.S);
        imovelDTO.setEndereco(objectMapper.convertValue(enderecoEntity, EnderecoDTO.class));
        imovelDTO.setDono(objectMapper.convertValue(clienteEntity, ClienteDTO.class));
        return imovelDTO;
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

    private ClienteEntity getClienteLocador() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setAtivo(Tipo.S);
        clienteEntity.setCpf("12345678910");
        clienteEntity.setEmail("cliente@mail.com");
        clienteEntity.setIdCliente(2);
        clienteEntity.setNome("Locador");
        return clienteEntity;
    }
}
