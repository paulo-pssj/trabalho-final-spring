package br.com.shinigami.security;

import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FuncionarioEntity> funcionario = funcionarioRepository.findByLogin(username);

        return funcionario.orElseThrow(() -> new UsernameNotFoundException("Usuário inválido"));
    }
}
