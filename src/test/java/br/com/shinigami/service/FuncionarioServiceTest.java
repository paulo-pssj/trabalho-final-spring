package br.com.shinigami.service;


import br.com.shinigami.dto.funcionario.AlterarSenhaDTO;
import br.com.shinigami.dto.funcionario.FuncionarioAtualizarDTO;
import br.com.shinigami.dto.funcionario.FuncionarioDTO;
import br.com.shinigami.dto.funcionario.LoginDTO;
import br.com.shinigami.entity.CargoEntity;
import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.repository.CargoRepository;
import br.com.shinigami.repository.FuncionarioRepository;
import br.com.shinigami.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private EmailService emailService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private LogService logService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(funcionarioService, "objectMapper", objectMapper);
    }

//    @Test
//    public void deveTestarRetornaTokenComSucesso(){
//        String login = "paulo";
//        String senha = "1234";
//        String token = "token";
//        CargoEntity cargo = new CargoEntity();
//        FuncionarioEntity funcionario = new FuncionarioEntity(1,"teste@gmail.com", login, senha, Tipo.S, "1", cargo);
//
//        LoginDTO loginDTO = new LoginDTO();
//        loginDTO.setLogin(login);
//        loginDTO.setSenha(senha);
//
//        Authentication authenticate = new UsernamePasswordAuthenticationToken(login, senha);
//
//        when(tokenService.getToken(any())).thenReturn(token);
//        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
//
//        String tokenRetorno = funcionarioService.retornaTokenFuncionario(loginDTO);
//
//        assertEquals(token, tokenRetorno);
//    }

    @Test
    public void deveTestarTokenTrocaDeSenha() throws RegraDeNegocioException{
        String msg = "Token de recuperação de senha enviado para seu email";
        String token = "token";

        FuncionarioEntity funcionario = getFuncionario();

        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        when(tokenService.getTokenTrocaDeSenha(any())).thenReturn(token);
        String msgRetorno = funcionarioService.retornarTokenTrocaDeSenha(funcionario.getEmail());

        assertEquals(msg, msgRetorno);
    }

    @Test
    public void deveTestarDesativarFuncionario()throws RegraDeNegocioException{
        FuncionarioEntity funcionario = getFuncionario();

        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        funcionarioService.desativarFuncionario(funcionario.getEmail());

        verify(funcionarioRepository, times(1)).save(any());

    }

    @Test
    public void deveTestarAtualizarFuncionario() throws RegraDeNegocioException{
        UsernamePasswordAuthenticationToken dto
                        = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        FuncionarioEntity funcionario = getFuncionario();


        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        when(funcionarioRepository.save(any())).thenReturn(getFuncionarioAtualizado());


        FuncionarioDTO funcionarioDTO = funcionarioService.atualizarFuncionario(getFuncionarioAtualizarDTO());

        assertEquals("teste", funcionarioDTO.getLogin());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAtualizarFunciionarioComErro() throws RegraDeNegocioException{
        UsernamePasswordAuthenticationToken UserPassAuthToken
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(UserPassAuthToken);
        FuncionarioEntity funcionario = getFuncionario();

        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        when(funcionarioRepository.save(any())).thenReturn(null);


        funcionarioService.atualizarFuncionario(getFuncionarioAtualizarDTO());

    }

    @Test
    public void deveTestarAlterarSenhaComSucesso() throws RegraDeNegocioException{
        UsernamePasswordAuthenticationToken UserPassAuthToken
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(UserPassAuthToken);
        FuncionarioEntity funcionario = getFuncionario();

        String msg = "Senha alterada com sucesso.";

        AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO();
        alterarSenhaDTO.setSenha("senha");

        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        funcionario.setSenha("senha");
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        String msgRetorno = funcionarioService.alterarSenha(alterarSenhaDTO);

        assertEquals(msg, msgRetorno);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAlterarSenhaComErro() throws RegraDeNegocioException{
        UsernamePasswordAuthenticationToken UserPassAuthToken
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(UserPassAuthToken);
        FuncionarioEntity funcionario = getFuncionario();

        String msg = "Senha alterada com sucesso.";

        AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO();
        alterarSenhaDTO.setSenha("senha");

        when(funcionarioRepository.findByEmail(any())).thenReturn(funcionario);
        funcionario.setSenha("senha");
        when(funcionarioRepository.save(any())).thenReturn(null);

        funcionarioService.alterarSenha(alterarSenhaDTO);

    }

    @Test
    public void deveTestarGetLoggedUser()throws RegraDeNegocioException{
        UsernamePasswordAuthenticationToken UserPassAuthToken
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(UserPassAuthToken);
        FuncionarioEntity funcionario = getFuncionario();

        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.of(funcionario));

        FuncionarioDTO funcionarioRetorno = funcionarioService.getLoggedUser();

        assertNotNull(funcionarioRetorno);
    }

    public  FuncionarioEntity getFuncionario() {
        String login = "paulo";
        String senha = "1234";
        CargoEntity cargo = new CargoEntity();
        return new FuncionarioEntity(1, "teste@gmail.com", login, senha, Tipo.S, "1", cargo);
    }

    public  FuncionarioEntity getFuncionarioAtualizado() {
        String login = "teste";
        String senha = "1234";
        CargoEntity cargo = new CargoEntity();
        return new FuncionarioEntity(1, "teste@gmail.com", login, senha, Tipo.S, "1", cargo);
    }

    public FuncionarioAtualizarDTO getFuncionarioAtualizarDTO() {
        FuncionarioAtualizarDTO funcionarioAtualizarDTO = new FuncionarioAtualizarDTO();
        funcionarioAtualizarDTO.setEmail("teste@email.com");
        funcionarioAtualizarDTO.setLogin("teste");
        funcionarioAtualizarDTO.setIdCargo(1);
        return funcionarioAtualizarDTO;
    }
}
