package br.com.shinigami.service;

import br.com.shinigami.exceptions.RegraDeNegocioException;

import java.util.List;

public interface ServiceInterface<DTO,CreateDTO>{

    List<DTO> list()throws RegraDeNegocioException;

    DTO create(CreateDTO obj) throws RegraDeNegocioException;

    DTO update(Integer id,CreateDTO obj) throws RegraDeNegocioException;
    void delete(Integer id) throws RegraDeNegocioException;

}
