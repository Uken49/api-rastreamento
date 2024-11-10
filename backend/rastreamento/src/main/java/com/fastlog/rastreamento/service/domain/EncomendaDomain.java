package com.fastlog.rastreamento.service.domain;

import java.util.UUID;
import lombok.Builder;

public record EncomendaDomain(
        UUID id,
        String nome,
        String origem,
        String destino,
        StatusDomain status
) {

    @Builder(toBuilder = true)
    public EncomendaDomain {
    }
}
