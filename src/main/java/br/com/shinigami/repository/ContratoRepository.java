package br.com.shinigami.repository;

import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.entity.ContratoEntity;
import br.com.shinigami.entity.Tipo;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, Integer> {

    Page<ContratoEntity> findAllByAtivo(Tipo ativo, Pageable pageable);

    @Query(" select new br.com.shinigami.dto.RelatorioContratoClienteDTO( "+
            "c.idContrato, "+
            "c.dataEntrada, "+
            "c.dataVencimento, "+
            "c.valorAluguel, "+
            "l.nome, "+
            "l.cpf, "+
            "l.email, "+
            "l.tipoCliente, "+
            "cl.nome, "+
            "cl.cpf, "+
            "cl.email, " +
            "cl.tipoCliente "+
            ")"+
            "from Contrato c "+
            "join c.locador l "+
            "join c.locatario cl "+
            "where(:idContrato is null or c.idContrato = :idContrato)"
    )
    List<RelatorioContratoClienteDTO> RelatorioContratoCliente(@Param("idContrato") Integer idContrato);


}
