package br.com.shinigami.controller;

import br.com.shinigami.controller.documentinterface.LogControllerInterface;
import br.com.shinigami.dto.AggregateTipoLogDTO;
import br.com.shinigami.dto.log.LogDTO;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController implements LogControllerInterface {

    private final LogService logService;


    @GetMapping("/log-por-tipo-data/")
    public ResponseEntity<List<LogDTO>> listPorTipoLog(@RequestParam(required = false) TipoLog tipoLog,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) throws RegraDeNegocioException {
        log.info("Listando Logs");
        List<LogDTO> lista = logService.listByDataETipo(tipoLog, data);
        log.info("Logs listados com sucesso");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/quantidade-Log")
    public ResponseEntity<List<AggregateTipoLogDTO>> groupByLog() {
        List<AggregateTipoLogDTO> aggregateTipoLogDTO = logService.groupByLog();
        return new ResponseEntity<>(aggregateTipoLogDTO, HttpStatus.OK);
    }
}
