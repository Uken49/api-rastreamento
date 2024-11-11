package com.fastlog.rastreamento.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RespostaConsultarEncomendaDto(
        UUID id,
        String nome,
        String origem,
        String destino,
        RespostaConsultarEncomendaDto.RespostaConsultarStatusDto status
) {

    public record RespostaConsultarStatusDto(
            String descricao,
            String pais,
            String cidade,
            LocalDateTime dataCriacao
    ) {

    }
}
