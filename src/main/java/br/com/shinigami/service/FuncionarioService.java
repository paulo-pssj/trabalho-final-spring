package br.com.shinigami.service;

import br.com.shinigami.dto.funcionario.FuncionarioCreateDTO;
import br.com.shinigami.dto.funcionario.FuncionarioDTO;
import br.com.shinigami.dto.funcionario.LoginDTO;
import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.entity.Tipo;
import br.com.shinigami.repository.CargoRepository;
import br.com.shinigami.repository.FuncionarioRepository;
import br.com.shinigami.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<FuncionarioEntity> findByLogin(String login) {
        return funcionarioRepository.findByLogin(login);
    }

    public String retornaTokenFuncionario(LoginDTO loginDTO) {
        Optional<FuncionarioEntity> funcionario = findByLogin(loginDTO.getLogin());

        if (funcionario.isPresent()) {
            return tokenService.getToken(funcionario.get());
        }
        return null;
    }

    public FuncionarioDTO create(FuncionarioCreateDTO funcionarioNovo) {
        FuncionarioEntity funcionario = objectMapper.convertValue(funcionarioNovo, FuncionarioEntity.class);
        funcionario.setAtivo(Tipo.S);
        funcionario.setSenha(passwordEncoder.encode(funcionarioNovo.getSenha()));
        funcionario.setCargo(cargoRepository.findById(funcionarioNovo.getIdCargo()).get());
        funcionarioRepository.save(funcionario);

        // ADICIONAR ENVIO DE EMAIL

        return objectMapper.convertValue(funcionario, FuncionarioDTO.class);

    }
}
