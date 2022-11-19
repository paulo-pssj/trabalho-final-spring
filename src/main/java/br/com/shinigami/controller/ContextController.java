package br.com.shinigami.controller;

import br.com.shinigami.dto.context.ContextDTO;
import br.com.shinigami.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mapa")
public class ContextController {

    private final ContextService contextService;

    @GetMapping
    public ResponseEntity<List<ContextDTO>> listar(){

    return new ResponseEntity<>(contextService.listarLatitudeLongitude(), HttpStatus.OK);
    }
}
