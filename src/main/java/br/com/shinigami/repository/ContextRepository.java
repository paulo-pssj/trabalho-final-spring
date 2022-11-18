package br.com.shinigami.repository;

import br.com.shinigami.entity.ContextEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContextRepository extends MongoRepository<ContextEntity,Integer> {
}
