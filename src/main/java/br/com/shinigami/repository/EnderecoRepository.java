package br.com.shinigami.repository;


import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

//    List<Endereco> findAllByAtivo(Tipo ativo);
//
//    Endereco findByIdEbderecoAndAtivo(Integer idEndereco, Tipo ativo);

}
