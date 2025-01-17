package br.com.shinigami.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Cargo")
public class CargoEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @SequenceGenerator(name = "CARGO_SEQ", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "id_cargo")
    private int idCargo;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo")
    private Set<FuncionarioEntity> funcionarios;

    @Override
    public String getAuthority() {
        return nome;
    }
}
