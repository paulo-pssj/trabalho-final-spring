package br.com.shinigami.service;

import br.com.shinigami.dto.context.ContextDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.entity.ContextEntity;
import br.com.shinigami.repository.ContextRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContextServiceTest {

    @Mock
    private ContextService contextService;

    @Mock
    private ContextRepository contextRepository;

    @Value("${apikey}")
    private String apiKey;

    @Test
    public void deveTestarListarLatideLongitudeComSucesso() {

        List<ContextEntity> listaEntity = new ArrayList<>();
        ContextEntity contextEntity = new ContextEntity(1, "12345", "54321");
        listaEntity.add(contextEntity);


//        when(contextRepository.findAll()).thenReturn(listaEntity);

        List<ContextDTO> lista = contextService.listarLatitudeLongitude();

        assertNotNull(lista);
    }

    @Test
    public void deveTestarDeleteComSucesso() {

        Integer id = 1;
        ContextEntity contextEntity = new ContextEntity(1, "12345", "54321");

//        when(contextRepository.findByIdEndereco(anyInt())).thenReturn(contextEntity);

        contextService.delete(id);

        verify(contextService, times(1)).delete(anyInt());
    }

//    @Test
//    public void deveTestarGerarContextComSucesso() throws IOException, InterruptedException, ApiException {
//
//        EnderecoDTO endereco = new EnderecoDTO();
//        endereco.setIdEndereco(1);
//        endereco.setRua("Rua");
//        endereco.setNumero(10);
//        endereco.setCidade("Poa");
//        endereco.setCep("12345678910");
//        endereco.setEstado("RS");
//        endereco.setPais("Brasil");
//
//        String enderecoConcatenado = endereco.getRua() + " "
//                + endereco.getNumero() + ", "
//                + endereco.getCidade() + ", "
//                + endereco.getCep() + ","
//                + endereco.getEstado() + ", "
//                + endereco.getPais();
//
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey(apiKey)
//                .build();
//        GeocodingResult[] results = GeocodingApi.geocode(context,enderecoConcatenado).await();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        ContextEntity contextEntity = new ContextEntity(
//                endereco.getIdEndereco(),gson.toJson(results[0].geometry.location.lng),
//                gson.toJson(results[0].geometry.location.lat));
//        contextRepository.save(contextEntity);
//
//
//        when(GeocodingApi.geocode(any(),anyString())).thenReturn(any());
////        ContextEntity contextEntity = new ContextEntity(1,"12345","54321");
//
////        when(contextRepository.save(any())).thenReturn(contextEntity);
//
//        contextService.gerarContext(endereco);
//
//        verify(contextService, times(1)).gerarContext(any());
//
//    }
}
