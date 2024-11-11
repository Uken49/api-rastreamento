package com.fastlog.rastreamento.factory;

import com.fastlog.rastreamento.infrastructure.repository.entity.StatusEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public class StatusEntityFactory {

    public static StatusEntity statusValido() {
        return new StatusEntity(
                UUID.randomUUID(),
                "Informações enviadas para análise da autoridade aduaneira/órgãos anuentes",
                "Brazil",
                "Sao Paulo - SP",
                LocalDateTime.now()
        );
    }
}
