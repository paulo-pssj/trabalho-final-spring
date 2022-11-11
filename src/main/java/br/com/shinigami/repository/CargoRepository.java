package br.com.shinigami.repository;

import br.com.shinigami.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
