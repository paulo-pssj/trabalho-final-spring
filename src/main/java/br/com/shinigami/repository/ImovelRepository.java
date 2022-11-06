package br.com.shinigami.repository;

import br.com.shinigami.dto.RelatorioImovelEnderecoDTO;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Integer> {

    Page<Imovel> findAllByAtivo(Tipo ativo, Pageable pageable);


    Imovel findByIdImovelAndAtivo(Integer idImovel, Tipo ativo);

    List<Imovel> findAllByAlugadoAndAtivo(Tipo alugado, Tipo ativo);

    @Query(" select new br.com.shinigami.dto.RelatorioImovelEnderecoDTO(" +
            "c.idCliente, " +
            "c.nome, " +
            "c.email, " +
            "i.idImovel, " +
            "i.tipoImovel, " +
            "i.valorMensal, " +
            "e.cidade, " +
            "e.estado, " +
            "e.pais " +
            ")" +
            " from IMOVEL i " +
            " join i.cliente c " +
            " join i.endereco e " +
            "where(:id is null or i.idImovel = :id )")
    List<RelatorioImovelEnderecoDTO> retornarRelatorioImovelEnderecoDTO(@Param("id") Integer id);

}

