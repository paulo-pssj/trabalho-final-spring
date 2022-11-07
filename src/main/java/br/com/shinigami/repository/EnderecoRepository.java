package br.com.shinigami.repository;


import br.com.shinigami.entity.EnderecoEntity;
import br.com.shinigami.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

    List<EnderecoEntity> findAllByAtivo(Tipo ativo);
//
//    Endereco findByIdEnderecoAndAtivo(Integer idEndereco, Tipo ativo);

}
