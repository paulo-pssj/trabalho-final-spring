package br.com.shinigami.entity;

import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "CLIENTE")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTE_SEQ")
    @SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "ATIVO")
    @Enumerated(EnumType.ORDINAL)
    private Tipo ativo;

    @Column(name = "TIPO_CLIENTE")
    @Enumerated(EnumType.ORDINAL)
    private TipoCliente tipoCliente;

    // ver a relação
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private Set<ImovelEntity> imoveis;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locador")
    private Set<ContratoEntity> contratosLocador;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locatario")
    private Set<ContratoEntity> contratosLocatario;

}
