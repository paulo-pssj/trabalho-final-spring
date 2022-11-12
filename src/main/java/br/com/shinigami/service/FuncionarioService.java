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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;



    private FuncionarioEntity findByEmail(String email){return funcionarioRepository.findByEmail(email);}

    public String retornaTokenFuncionario(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getLogin(),
                loginDTO.getSenha()
        );

        Authentication authenticate = authenticationManager.authenticate(userPassAuthToken);

        Object principal = authenticate.getPrincipal();
        FuncionarioEntity funcionario = (FuncionarioEntity) principal;
        return tokenService.getToken(funcionario);

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

    public String tokenTrocaDeSenha(String email){
        FuncionarioEntity funcionario = findByEmail(email);
        if(funcionario != null){
            return tokenService.getTokenTrocaDeSenha(funcionario);
        }
        return "Funcionario não existe!";
    }
}
