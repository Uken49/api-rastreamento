package com.fastlog.rastreamento.service;

import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapper;
import com.fastlog.rastreamento.infrastructure.repository.EncomendaRepository;
import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaCadastrarEncomendaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarEncomenda {

    private final EncomendaRepository encomendaRepository;
    private final EncomendaMapper encomendaMapper;

    public RespostaCadastrarEncomendaDto cadastarEncomenda(final CadastrarEncomendaDto dadosEncomenda) {
        final EncomendaEntity dadosEncomendaEntity = encomendaMapper.cadastarEncomendaDtoParaEncomendaEntity(dadosEncomenda);

        final EncomendaEntity encomendaEntitySalvo = encomendaRepository.save(dadosEncomendaEntity);

        return encomendaMapper.encomendaEntityParaRespostaCadastrarEncomendaDto(encomendaEntitySalvo);
    }
}
