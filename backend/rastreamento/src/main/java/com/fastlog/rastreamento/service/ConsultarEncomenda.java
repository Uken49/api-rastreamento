package com.fastlog.rastreamento.service;

import com.fastlog.rastreamento.infrastructure.handler.exception.EncomendaNaoEncontradaException;
import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapper;
import com.fastlog.rastreamento.infrastructure.repository.EncomendaRepository;
import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import com.fastlog.rastreamento.presentation.dto.response.RespostaConsultarEncomendaDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarEncomenda {

    private final EncomendaRepository encomendaRepository;
    private final EncomendaMapper encomendaMapper;

    public RespostaConsultarEncomendaDto consultarEncomendaPorId(final UUID id) {

        final EncomendaEntity encomendaConsultada = encomendaRepository.findById(id).orElseThrow(
                () -> new EncomendaNaoEncontradaException("A encomenda com o id não %s foi encontrada.".formatted(id))
        );

        return encomendaMapper.encomendaEntityParaRespostaConsultarEncomendaDto(encomendaConsultada);
    }

}
