package br.com.shinigami.repository;


import br.com.shinigami.entity.ClienteEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {


    List<ClienteEntity> findByTipoClienteAndAtivo(TipoCliente tipo, Tipo ativo);

    List<ClienteEntity> findAllByAtivo(Tipo ativo);

    ClienteEntity findByIdClienteAndAtivo(Integer idCliente, Tipo ativo);


}
