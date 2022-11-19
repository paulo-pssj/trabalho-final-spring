package br.com.shinigami;

import br.com.shinigami.dto.log.LogCreateDTO;
import br.com.shinigami.dto.log.LogDTO;
import br.com.shinigami.entity.LogEntity;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;


    public void create(LogCreateDTO logCreateDTO){
        logRepository.save(objectMapper.convertValue(logCreateDTO, LogEntity.class));
    }

    private List<LogDTO> list(){
        return logRepository.findAll()
                .stream()
                .map(log ->objectMapper.convertValue(log, LogDTO.class))
                .toList();
    }

    private List<LogDTO> listByTipoLog(TipoLog tipoLog){
        if (tipoLog == null){
            return list();
        }
        return logRepository.findAllByTipoLog(tipoLog)
                .stream()
                .map(log-> objectMapper.convertValue(log, LogDTO.class))
                .toList();
    }

    public List<LogDTO> listByDataETipo(TipoLog tipoLog, LocalDate data){
        if (tipoLog == null && data != null){
            return logRepository.findAllByData(data)
                    .stream()
                    .map(logEntity -> objectMapper.convertValue(logEntity,LogDTO.class))
                    .toList();
        } else if (data == null && tipoLog != null){
            return listByTipoLog(tipoLog);
        } else if (data == null && tipoLog == null){
            return list();
        } else {
            return logRepository.findAllByTipoLogAndData(tipoLog,data)
                    .stream()
                    .map(log-> objectMapper.convertValue(log, LogDTO.class))
                    .toList();
        }
    }
}
