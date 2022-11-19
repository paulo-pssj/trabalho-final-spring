package br.com.shinigami;

import br.com.shinigami.dto.context.ContextDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.entity.ContextEntity;
import br.com.shinigami.repository.ContextRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContextService {

    private final ContextRepository contextRepository;
    private final ObjectMapper objectMapper;
    @Value("${apikey}")
    private String apiKey;

    public void gerarContext(EnderecoDTO endereco) throws IOException, InterruptedException,ApiException {

      String enderecoConcatenado = endereco.getRua() + " "
                                   + endereco.getNumero() + ", "
                                   + endereco.getCidade() + ", "
                                   + endereco.getCep() + ","
                                   + endereco.getEstado() + ", "
                                   + endereco.getPais();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context,enderecoConcatenado).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ContextEntity contextEntity = new ContextEntity(
                endereco.getIdEndereco(),gson.toJson(results[0].geometry.location.lng),
                gson.toJson(results[0].geometry.location.lat));
        contextRepository.save(contextEntity);

        context.shutdown();
    }

    public List<ContextDTO> listarLatitudeLongitude() {

        List<ContextDTO> listaContext = contextRepository.findAll().stream()
                .map(contextEntity -> objectMapper.convertValue(contextEntity,ContextDTO.class))
                .toList();

        return listaContext;
    }

    public void delete(Integer idEndereco){
     ContextEntity enderecoContext= contextRepository.findByIdEndereco(idEndereco);
        contextRepository.delete(enderecoContext);
    }
}
