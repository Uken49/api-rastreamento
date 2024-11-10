package com.fastlog.rastreamento.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RespostaCadastrarEncomendaDto(
        UUID id,
        String nome,
        String origem,
        String destino,
        RespostaCadastrarStatusDto status
) {

    public record RespostaCadastrarStatusDto(
            String descricao,
            String pais,
            String cidade,
            LocalDateTime dataCriacao
    ) {

    }
}
