package com.fastlog.rastreamento.infrastructure.mapper;

import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaCadastrarEncomendaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EncomendaMapper {
    EncomendaEntity cadastarEncomendaDtoParaEncomendaEntity(CadastrarEncomendaDto cadastrarEncomendaDto);

    RespostaCadastrarEncomendaDto encomendaEntityParaRespostaCadastrarEncomendaDto(EncomendaEntity encomendaEntity);
}
