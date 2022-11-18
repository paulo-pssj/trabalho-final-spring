package br.com.shinigami.dto.log;

import br.com.shinigami.entity.enums.TipoLog;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LogDTO {

    private String id;

    private TipoLog tipoLog;

    private String descricao;

    private LocalDate data;
}
