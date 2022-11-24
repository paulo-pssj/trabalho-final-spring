package br.com.shinigami.service;

import br.com.shinigami.dto.cupom.CupomCreateDTO;
import br.com.shinigami.dto.cupom.CupomDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutorService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value(value= "${kafka.topic}")
    private final String topic;
    private final double desconto = 5;
    private final int expiracao=5;

    public void enviarCupom(String email) throws JsonProcessingException {

        CupomCreateDTO cupomCreateDTO = new CupomCreateDTO();
        cupomCreateDTO.setDataCriacao(LocalDate.now());
        cupomCreateDTO.setDataVencimento(cupomCreateDTO.getDataCriacao().plusDays(expiracao));
        cupomCreateDTO.setEmail(email);
        cupomCreateDTO.setDesconto(desconto);

        String msg = objectMapper.writeValueAsString(cupomCreateDTO);
        // mensagem, chave, topico
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.PARTITION_ID, "0");

        ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(stringMessageBuilder.build());
        enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult result) {
                log.info(" Log enviado para o kafka com o texto: {} ", cupomCreateDTO);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(" Erro ao publicar duvida no kafka com a mensagem: {}", cupomCreateDTO, ex);
            }
        });

    }

}
