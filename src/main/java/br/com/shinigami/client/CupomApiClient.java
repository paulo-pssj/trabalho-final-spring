package br.com.shinigami.client;

import br.com.shinigami.dto.cupom.CupomDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="cupom-api", url="http://vemser-dbc.dbccompany.com.br:39000/rlangbecker/cupom-api")
@Headers("Content-Type: application/json")
public interface CupomApiClient {

    @RequestLine("GET /cupom")
    CupomDTO findByEmail(@RequestParam ("email") String email);

    @RequestLine("GET /cupom/desativar-cupom")
    void desativarCupom(@RequestParam ("email") String email);
}
