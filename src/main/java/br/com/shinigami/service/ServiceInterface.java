package br.com.shinigami.service;

import br.com.shinigami.exceptions.RegraDeNegocioException;
import com.google.maps.errors.ApiException;

import java.io.IOException;

public interface ServiceInterface<DTO, CreateDTO> {

    DTO create(CreateDTO obj) throws RegraDeNegocioException, IOException, InterruptedException, ApiException;

    DTO update(Integer id, CreateDTO obj) throws RegraDeNegocioException;

    void delete(Integer id) throws RegraDeNegocioException;

}
