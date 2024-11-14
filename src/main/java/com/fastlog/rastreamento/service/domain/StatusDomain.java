package com.fastlog.rastreamento.service.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record StatusDomain(
        UUID id,
        String descricao,
        String pais,
        String cidade,
        LocalDateTime dataCriacao
) {

    @Builder(toBuilder = true)
    public StatusDomain {
    }
}
