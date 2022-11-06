package br.com.shinigami.repository;


import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.relatorio.RelatorioImovelEnderecoDTO;
import br.com.shinigami.model.Endereco;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

//    List<Endereco> findAllByAtivo(Tipo ativo);
//
//    Endereco findByIdEbderecoAndAtivo(Integer idEndereco, Tipo ativo);

}
