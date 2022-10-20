package br.com.shinigami.service;


import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;

public interface ServiceInterface<T> {
    boolean adicionar(T obj) throws RegraDeNegocioException, BancoDeDadosException;

    void listar() throws BancoDeDadosException;

    void editar(Integer idx, T obj) throws RegraDeNegocioException,BancoDeDadosException;

    boolean remover(Integer idx) throws RegraDeNegocioException,BancoDeDadosException;
}
