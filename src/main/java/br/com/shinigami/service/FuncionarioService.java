package br.com.shinigami.service;

import br.com.shinigami.dto.funcionario.FuncionarioCreateDTO;
import br.com.shinigami.dto.funcionario.FuncionarioDTO;
import br.com.shinigami.dto.funcionario.LoginDTO;
import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.entity.Tipo;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.CargoRepository;
import br.com.shinigami.repository.FuncionarioRepository;
import br.com.shinigami.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public String tokenTrocaDeSenha(String email)throws RegraDeNegocioException{
        FuncionarioEntity funcionario = findByEmail(email);
        return tokenService.getTokenTrocaDeSenha(funcionario);
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

    public FuncionarioDTO getLoggedUser() throws RegraDeNegocioException{
        String idFuncionario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(idFuncionario.equals("anonymousUser")){
            throw new RegraDeNegocioException("Usuario anonimo.");
        }
        Integer idUserLogged = Integer.valueOf(idFuncionario);
        FuncionarioDTO funcionario = objectMapper.convertValue(funcionarioRepository.findById(idUserLogged), FuncionarioDTO.class);

        return funcionario;
    }

    public void desativarFuncionario(String email) throws RegraDeNegocioException{
        FuncionarioEntity funcionario = findByEmail(email);
        funcionario.setAtivo(Tipo.N);
        funcionarioRepository.save(funcionario);
    }

    private FuncionarioEntity findByEmail(String email)throws RegraDeNegocioException{
        FuncionarioEntity funcionario = funcionarioRepository.findByEmail(email);
        if(funcionario == null){
            throw new RegraDeNegocioException("Funcionario não existe.");
        }
        return funcionario;
    }
}
