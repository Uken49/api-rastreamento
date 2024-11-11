package com.fastlog.rastreamento;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RastreamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RastreamentoApplication.class, args);
    }

    @Component
    public static class Runner implements ApplicationRunner {

        @Value("${profiles.active}")
        private String ambiente;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("Aplicação rodando no ambiente: " + ambiente);
        }
    }

}
