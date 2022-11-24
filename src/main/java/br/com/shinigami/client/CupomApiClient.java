package br.com.shinigami.client;

import br.com.shinigami.dto.cupom.CupomDTO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value="cupom-api", url="http://localhost:8090 ")
@Headers("Content-Type: application/json")
public interface CupomApiClient {

    @RequestLine("GET /cupom")
    CupomDTO findByEmail(String email);

}
