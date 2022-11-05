package br.com.shinigami.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "ENDERECO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Endereco{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECO_SEQ")
    @SequenceGenerator(name = "ENDERECO_SEQ", sequenceName = "seq_endereco", allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "PAIS")
    private String pais;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "CEP")
    private String cep;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endereco")
    private Set<Imovel> imovel;
}
