package br.com.shinigami.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/Imovel")
public class ImovelController {

}
