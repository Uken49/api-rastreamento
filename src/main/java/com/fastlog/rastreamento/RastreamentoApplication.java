package com.fastlog.rastreamento;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RastreamentoApplication {

    private static final List<String> AMBIENTES_PERMITIDOS = List.of("prod", "local", "local-docker", "test");

    public static void main(String[] args) {
        SpringApplication.run(RastreamentoApplication.class, args);
    }

    @Component
    public static class Runner implements ApplicationRunner {

        @Value("${spring.profiles.active}")
        private String ambiente;

        @Override
        public void run(ApplicationArguments args) {
            if (!AMBIENTES_PERMITIDOS.contains(ambiente)) {
                System.exit(1);
                throw new IllegalArgumentException(
                        "Variável 'SPRING_APP_ENVIRONMENT' deve ser preenchida com 'prod', 'local', 'local-docker' ou 'test'."
                );
            }

            System.out.println("Aplicação rodando no ambiente: " + ambiente);
        }
    }

}
