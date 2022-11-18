package br.com.shinigami.entity;

import br.com.shinigami.entity.enums.TipoLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.time.LocalDate;


@Getter
@Setter
@Document ( collection = "logs")
public class LogEntity {

    @Id
    private String id;

    private TipoLog tipoLog;

    private String descricao;

    private LocalDate data;

}
