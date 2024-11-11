package com.fastlog.rastreamento.infrastructure.mapper;

import com.fastlog.rastreamento.infrastructure.repository.entity.StatusEntity;
import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaCadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaConsultarEncomendaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusEntity cadastarEncomendaDtoParaStatusEntity(CadastrarEncomendaDto.CadastrarStatusDto cadastrarStatusDto);

    RespostaCadastrarEncomendaDto.RespostaCadastrarStatusDto statusEntityParaRespostaCadastrarStatusDto(StatusEntity statusEntity);

    RespostaConsultarEncomendaDto.RespostaConsultarStatusDto statusEntityParaRespostaConsultarStatusDto(StatusEntity statusEntity);

}
