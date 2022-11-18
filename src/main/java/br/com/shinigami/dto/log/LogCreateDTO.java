package br.com.shinigami.dto.log;

import br.com.shinigami.entity.enums.TipoLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogCreateDTO {

    private TipoLog tipoLog;
    private String descricao;
    private LocalDate data;

}
