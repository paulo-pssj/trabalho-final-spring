package br.com.shinigami.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "IMOVEL")
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMOVEL_SEQ")
    @SequenceGenerator(name = "IMOVEL_SEQ", sequenceName = "SEQ_IMOVEL", allocationSize = 1)
    @Column(name = "ID_IMOVEL")
    private int idImovel;

    @Column(name = "QNTD_QUARTOS")
    private int qntdQuartos;

    @Column(name = "QNTD_BANHEIROS")
    private int qntdBanheiros;

    @Column(name = "VALOR_MENSAL")
    private double valorMensal;

    @Column(name = "CONDOMINIO")
    private double condominio;

    @Column(name = "TIPO_IMOVEL")
    @Enumerated(EnumType.ORDINAL)
    private TipoImovel tipoImovel;

    @Column(name = "ALUGADO")
    @Enumerated(EnumType.ORDINAL)
    private Tipo alugado;

    @Column(name = "AREA_DE_LAZER")
    @Enumerated(EnumType.ORDINAL)
    private Tipo areaDeLazer;

    @Column(name = "GARAGEM")
    @Enumerated(EnumType.ORDINAL)
    private Tipo garagem;

    @Column(name = "PERMITE_ANIMAIS")
    @Enumerated(EnumType.ORDINAL)
    private Tipo permiteAnimais;

    @Column(name = "SALAO_DE_FESTAS")
    @Enumerated(EnumType.ORDINAL)
    private Tipo salaoDeFesta;

    @Column(name = "NUMERO_DE_VAGAS")
    private int numeroDeVagas;

    @Column(name = "ATIVO")
    @Enumerated(EnumType.ORDINAL)
    private Tipo ativo;

    @Column(name = "ID_CLIENTE", insertable = false, updatable = false)
    private Integer idDono;

    @Column(name = "ID_ENDERECO", insertable = false, updatable = false)
    private Integer idEndereco;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "")
//    private Set<Contrato> contratos;
}
