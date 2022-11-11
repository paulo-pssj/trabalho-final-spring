package br.com.shinigami.service;

import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.repository.FuncionarioRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public Optional<FuncionarioEntity> findByLogin(String login) {
        return funcionarioRepository.findByLogin(login);
    }
}
