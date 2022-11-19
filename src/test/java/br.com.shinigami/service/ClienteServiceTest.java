package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {


    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void deveTestarCreateComSucesso() {

        // SETUP


        // ACT
        ClienteDTO clienteDTO = clienteService;


        // ASSERT

    }
}
