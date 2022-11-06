package br.com.shinigami.repository;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {


//    @Query(" select c " +
//            " from Cliente c "+
//            " join c.Contrato e ")
//    List<ClienteDTO> ClientesComContrato();

//    @Query(" select new br.com.shinigami.dto.RelatorioImovelEnderecoDTO("+
//            "c.idCliente "

}
