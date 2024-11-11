package com.fastlog.rastreamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastlog.rastreamento.factory.EncomendaEntityFactory;
import com.fastlog.rastreamento.infrastructure.handler.exception.EncomendaNaoEncontradaException;
import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapper;
import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapperImpl;
import com.fastlog.rastreamento.infrastructure.repository.EncomendaRepository;
import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import com.fastlog.rastreamento.presentation.dto.response.RespostaConsultarEncomendaDto;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsultarEncomendaTest {

    @Mock
    private EncomendaRepository repository;
    @Spy
    private EncomendaMapper mapper = new EncomendaMapperImpl();
    @InjectMocks
    private ConsultarEncomenda service;
    @Captor
    private ArgumentCaptor<EncomendaEntity> encomendaEntityCaptor;
    @Captor
    private ArgumentCaptor<UUID> idCaptor;

    @Test
    void deve_consultar_encomenda_corretamente() {
        final UUID id = UUID.randomUUID();
        final EncomendaEntity encomendaEntityMock = EncomendaEntityFactory.encomendaValida()
                .toBuilder()
                .id(id)
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(encomendaEntityMock));

        final RespostaConsultarEncomendaDto respostaService = service.consultarEncomendaPorId(id);

        verify(repository, times(1)).findById(any(UUID.class));
        verify(repository).findById(idCaptor.capture());
        verify(mapper).encomendaEntityParaRespostaConsultarEncomendaDto(encomendaEntityCaptor.capture());

        final UUID idCapturado = idCaptor.getValue();
        final EncomendaEntity encomendaEntityCapturado = encomendaEntityCaptor.getValue();

        assertEquals(id, idCapturado);
        assertEquals(id, encomendaEntityCapturado.getId());

        assertEquals(encomendaEntityMock.getId(), encomendaEntityCapturado.getId());
        assertEquals(encomendaEntityMock.getNome(), encomendaEntityCapturado.getNome());
        assertEquals(encomendaEntityMock.getOrigem(), encomendaEntityCapturado.getOrigem());
        assertEquals(encomendaEntityMock.getDestino(), encomendaEntityCapturado.getDestino());
        assertEquals(encomendaEntityMock.getStatus().getId(), encomendaEntityCapturado.getStatus().getId());
        assertEquals(encomendaEntityMock.getStatus().getDescricao(), encomendaEntityCapturado.getStatus().getDescricao());
        assertEquals(encomendaEntityMock.getStatus().getPais(), encomendaEntityCapturado.getStatus().getPais());
        assertEquals(encomendaEntityMock.getStatus().getCidade(), encomendaEntityCapturado.getStatus().getCidade());
        assertEquals(encomendaEntityMock.getStatus().getDataCriacao(), encomendaEntityCapturado.getStatus().getDataCriacao());

        assertEquals(encomendaEntityCapturado.getId(), respostaService.id());
        assertEquals(encomendaEntityCapturado.getNome(), respostaService.nome());
        assertEquals(encomendaEntityCapturado.getOrigem(), respostaService.origem());
        assertEquals(encomendaEntityCapturado.getDestino(), respostaService.destino());
        assertEquals(encomendaEntityCapturado.getStatus().getCidade(), respostaService.status().cidade());
        assertEquals(encomendaEntityCapturado.getStatus().getPais(), respostaService.status().pais());
        assertEquals(encomendaEntityCapturado.getStatus().getDescricao(), respostaService.status().descricao());
        assertEquals(encomendaEntityCapturado.getStatus().getDataCriacao(), respostaService.status().dataCriacao());
    }

    @Test
    void deve_retornar_EncomendaNaoEncontradaException_quando_id_for_encontrado() {
        final UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        final EncomendaNaoEncontradaException respostaService = assertThrows(EncomendaNaoEncontradaException.class,
                () -> service.consultarEncomendaPorId(id));

        verify(repository, times(1)).findById(any(UUID.class));
        verify(repository).findById(idCaptor.capture());

        assertEquals(id, idCaptor.getValue());
        assertEquals("A encomenda com o id não %s foi encontrada.".formatted(id), respostaService.getMessage());
    }
}