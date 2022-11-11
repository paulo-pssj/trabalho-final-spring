package br.com.shinigami.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Funcionario")
public class FuncionarioEntity implements UserDetails {

    Set<CargoEntity> cargos;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIO_SEQ")
    @SequenceGenerator(name = "FUNCIONARIO_SEQ", sequenceName = "seq_funcionario", allocationSize = 1)
    @Column(name = "id_funcionario")
    private int idFuncionario;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Tipo ativo;

    @Column(name = "id_cargo", insertable = false, updatable = false)
    private String idCargo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    private Set<CargoEntity> cargo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargo;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (getAtivo().equals("S")) {
            return true;
        }
        return false;
    }
}
