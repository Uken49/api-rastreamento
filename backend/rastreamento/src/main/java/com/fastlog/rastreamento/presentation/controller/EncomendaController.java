package com.fastlog.rastreamento.presentation.controller;

import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaCadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaConsultarEncomendaDto;
import com.fastlog.rastreamento.service.CadastrarEncomenda;
import com.fastlog.rastreamento.service.ConsultarEncomenda;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rastreamento/v1/encomendas")
@RequiredArgsConstructor
@Validated
public class EncomendaController {

    private final CadastrarEncomenda cadastrarEncomenda;
    private final ConsultarEncomenda consultarEncomenda;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespostaCadastrarEncomendaDto cadastrarEncomendaEndpoint(@RequestBody @Valid @NonNull CadastrarEncomendaDto dadosEncomenda) {
        return cadastrarEncomenda.cadastarEncomenda(dadosEncomenda);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RespostaConsultarEncomendaDto consultarEncomendaEndpoint(@PathVariable @NonNull UUID id) {
        return consultarEncomenda.consultarEncomendaPorId(id);
    }
}
