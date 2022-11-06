package br.com.shinigami.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRATO_SEQ")
    @SequenceGenerator(name = "CONTRATO_SEQ", sequenceName = "seq_contrato", allocationSize = 1)
    @Column(name = "id_contrato")
    private int idContrato;

    @Column(name = "valor_aluguel")
    private double valorAluguel;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Tipo ativo;

    @Column(name = "ID_LOCADOR", insertable = false, updatable = false)
    private int idLocador;

    @Column(name = "ID_LOCATARIO", insertable = false, updatable = false)
    private int idLocatario;

    @Column(name = "ID_IMOVEL", insertable = false, updatable = false)
    private int idImovel;

    // referenciando o id do imovel e fazendo um mapeamento de muitos para um(ManyToOne) com imovel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imovel", referencedColumnName = "id_imovel")
    private Imovel imovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_locador", referencedColumnName = "id_cliente")
    private Cliente locador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_locatario", referencedColumnName = "id_cliente")
    private Cliente locatario;

}
