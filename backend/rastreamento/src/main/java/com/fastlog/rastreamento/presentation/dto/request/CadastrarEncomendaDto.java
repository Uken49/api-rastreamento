package com.fastlog.rastreamento.presentation.dto.request;

import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;

public record CadastrarEncomendaDto(
        @Length(min = 3, max = 256, message = "O comprimento do nome deve ser entre 3 e 256")
        String nome,
        @Length(min = 3, max = 256, message = "O comprimento da origem deve ser entre 3 e 256")
        String origem,
        @Length(min = 3, max = 256, message = "O comprimento do destino deve ser entre 3 e 256")
        String destino,
        @Valid
        CadastrarStatusDto status
) {

    public record CadastrarStatusDto(
            @Length(min = 3, max = 256, message = "O comprimento da descrição deve ser entre 3 e 256")
            String descricao,
            @Length(min = 3, max = 256, message = "O comprimento do país deve ser entre 3 e 256")
            String pais,
            @Length(min = 3, max = 256, message = "O comprimento da cidade deve ser entre 3 e 256")
            String cidade
    ) {

    }
}
