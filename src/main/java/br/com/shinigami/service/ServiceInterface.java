package br.com.shinigami.service;

import br.com.shinigami.exceptions.RegraDeNegocioException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ServiceInterface<DTO,CreateDTO>{

    DTO create(CreateDTO obj) throws RegraDeNegocioException;

    DTO update(Integer id,CreateDTO obj) throws RegraDeNegocioException;

    void delete(Integer id) throws RegraDeNegocioException;

}
