package br.com.shinigami.dto;

import br.com.shinigami.entity.enums.TipoCliente;
import br.com.shinigami.entity.enums.TipoLog;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AggregateTipoLogDTO {

    @Field("_id")
    private TipoLog tipoLog;

    private Integer quantidade;
}
