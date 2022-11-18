package br.com.shinigami.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "endereco_lat_lgt")
public class ContextEntity {

    @Id
    private String id;

    private Integer idEndereco;

    private String longitude;

    private String latitude;

    public ContextEntity(Integer idEndereco, String longitude, String latitude) {
        this.idEndereco = idEndereco;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
