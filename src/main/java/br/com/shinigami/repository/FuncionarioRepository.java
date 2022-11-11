package br.com.shinigami.repository;

import br.com.shinigami.entity.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {

    Optional<FuncionarioEntity> findByLogin(String login);

}

