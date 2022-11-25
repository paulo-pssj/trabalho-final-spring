package br.com.shinigami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
@EnableFeignClients
public class ImobiliariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImobiliariaApplication.class, args);
    }
}
