package br.com.shinigami.repository;

import br.com.shinigami.dto.AggregateTipoLogDTO;
import br.com.shinigami.entity.LogEntity;
import br.com.shinigami.entity.enums.TipoLog;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface LogRepository extends MongoRepository<LogEntity, String> {

   List<LogEntity> findAllByTipoLog(TipoLog tipoLog);

   List<LogEntity> findAllByData(LocalDate data);

   List<LogEntity> findAllByTipoLogAndData(TipoLog tipoLog, LocalDate data);

   @Aggregation(pipeline = {
           "{'$group':{'_id': '$tipoLog', 'quantidade' : {'$sum': 1} }}"
   })
   List<AggregateTipoLogDTO> groupByTipo();

}
