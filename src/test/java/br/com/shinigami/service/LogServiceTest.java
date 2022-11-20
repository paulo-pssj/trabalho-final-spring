package br.com.shinigami.service;

import br.com.shinigami.dto.log.LogCreateDTO;
import br.com.shinigami.dto.log.LogDTO;
import br.com.shinigami.entity.LogEntity;
import br.com.shinigami.entity.enums.TipoLog;
import br.com.shinigami.repository.LogRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.shinigami.entity.enums.TipoLog.CLIENTE;
import static br.com.shinigami.entity.enums.TipoLog.ENDERECO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @InjectMocks
    private LogService logService;

    @Mock
    private LogRepository logRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(logService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateLogServiceComSucesso(){
        LogCreateDTO logCreateDTO = getLogCreateDTO();

        logService.create(logCreateDTO);

        verify(logRepository,times(1)).save(any());

    }
    @Test

    public void deveListarLogDataETipoPassandoDataComSucesso() {

        List<LogEntity> lista = new ArrayList<>();
        lista.add(getLogEntity());


        when(logRepository.findAllByData(any())).thenReturn(lista);

        List<LogDTO> list = logService.listByDataETipo(null, LocalDate.of(2022, 11, 22));

        assertNotNull(list);
        assertTrue(list.size() > 0);


    }

    @Test
    public void deveListarLogDataETipoPassandoTipoLogComSucesso() {

        List<LogEntity> lista = new ArrayList<>();
        lista.add(getLogEntity());


        when(logRepository.findAllByTipoLog(any())).thenReturn(lista);

        List<LogDTO> list = logService.listByDataETipo(ENDERECO, null);

        assertNotNull(list);
        assertTrue(list.size() > 0);

    }

    @Test
    public void deveListarLogDataETipoSemPassarParametrosComSucesso() {

        List<LogEntity> lista = new ArrayList<>();
        lista.add(getLogEntity());


        when(logRepository.findAll()).thenReturn(lista);

        List<LogDTO> list = logService.listByDataETipo(null, null);

        assertNotNull(list);
        assertTrue(list.size() > 0);

    }

    @Test
    public void deveListarLogDataETipoPassandoTipoEDataComSucesso() {

        List<LogEntity> lista = new ArrayList<>();
        lista.add(getLogEntity());


        when(logRepository.findAllByTipoLogAndData(any(), any())).thenReturn(lista);

        List<LogDTO> list = logService.listByDataETipo(ENDERECO, LocalDate.of(2022, 11, 22));

        assertNotNull(list);
        assertTrue(list.size() > 0);

    }

    private LogCreateDTO getLogCreateDTO() {
        return new LogCreateDTO(ENDERECO,"Criando endereco",LocalDate.of(2022, 11, 22));
    }


    private LogEntity getLogEntity() {
        LogEntity logEntity = new LogEntity();
        logEntity.setTipoLog(CLIENTE);
        logEntity.setId("1");
        logEntity.setDescricao("log cliente");
        logEntity.setData(LocalDate.of(2022, 11, 22));
        return logEntity;
    }
}