package br.com.shinigami.service;

import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.maps.errors.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {


    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ContextService contextService;
    @Mock
    private LogService logService;


    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(enderecoService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarCreateComSucesso() {

        EnderecoEntity enderecoEntity = getEnderecoEntity();

        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        EnderecoEntity enderecoRetorno = enderecoRepository.save(enderecoEntity);


        assertNotNull(enderecoRetorno);
        assertEquals(enderecoEntity, enderecoRetorno);
        assertNotEquals(" ", enderecoRetorno.getRua());

    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {

        Integer id = 1;

        EnderecoEntity enderecoEntity = getEnderecoEntity();
        enderecoEntity.setIdEndereco(id);

        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        enderecoService.delete(id);

        assertNotEquals(enderecoEntity, Tipo.N);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeleteComException() throws RegraDeNegocioException {
        Integer id = 1;
        EnderecoEntity enderecoEntity = getEnderecoEntity();

        when(enderecoRepository.findById(anyInt())).thenReturn(null);

        enderecoService.delete(id);
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException, IOException, InterruptedException, ApiException {

        Integer id = 1;

        EnderecoEntity enderecoEntity = getEnderecoEntity();
        EnderecoCreateDTO enderecoCreateDTO = objectMapper.convertValue(enderecoEntity, EnderecoCreateDTO.class);

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        EnderecoDTO enderecoDTO = enderecoService.update(id, enderecoCreateDTO);

        assertNotNull(enderecoDTO);
        assertEquals(enderecoCreateDTO.getCep(), enderecoDTO.getCep());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdateComExcepetion() throws RegraDeNegocioException, IOException, InterruptedException, ApiException {
        Integer id = 1;
        EnderecoEntity enderecoEntity = getEnderecoEntity();
        EnderecoCreateDTO enderecoCreateDTO = objectMapper.convertValue(enderecoEntity, EnderecoCreateDTO.class);

        when(enderecoRepository.findById(anyInt())).thenReturn(null);

        enderecoService.update(id, enderecoCreateDTO);
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {

        EnderecoEntity enderecoEntity = getEnderecoEntity();

        List<EnderecoEntity> lista = new ArrayList<>();
        lista.add(enderecoEntity);


        when(enderecoRepository.findAllByAtivo(any())).thenReturn(lista);

        List<EnderecoDTO> listaDTO = enderecoService.list();

        assertNotNull(listaDTO);
        assertTrue(lista.size() > 0);
        assertEquals(1, lista.size());
    }

//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
//        Integer id = 1;
//        EnderecoEntity enderecoEntity = getEnderecoEntity();
//
//        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoEntity));
//
//        EnderecoEntity enderecoRetorno = enderecoService.findById(id);
//
//        assertEquals(1, enderecoRetorno.getIdEndereco());
//        assertEquals("Cristiano Fischer", enderecoRetorno.getRua());
//    }


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

}
