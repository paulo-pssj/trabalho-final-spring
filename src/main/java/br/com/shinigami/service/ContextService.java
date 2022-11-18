package br.com.shinigami.service;

import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.entity.ContextEntity;
import br.com.shinigami.repository.ContextRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ContextService {

    private final ContextRepository contextRepository;
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

        ContextEntity contextEntity = new ContextEntity(endereco.getIdEndereco(),gson.toJson(results[0].geometry.location.lng), gson.toJson(results[0].geometry.location.lat));
        contextRepository.save(contextEntity);

        context.shutdown();
    }
}
