package br.com.shinigami.repository;


import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


    List<Cliente> findByTipoClienteAndAtivo(TipoCliente tipo, Tipo ativo);

    List<Cliente> findAllByAtivo(Tipo ativo);

    Cliente findByIdClienteAndAtivo(Integer idCliente, Tipo ativo);


}
