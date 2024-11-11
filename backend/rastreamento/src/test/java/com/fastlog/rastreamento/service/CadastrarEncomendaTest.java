package com.fastlog.rastreamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastlog.rastreamento.factory.CadastrarEncomendaFactory;
import com.fastlog.rastreamento.factory.EncomendaEntityFactory;
import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapper;
import com.fastlog.rastreamento.infrastructure.mapper.EncomendaMapperImpl;
import com.fastlog.rastreamento.infrastructure.repository.EncomendaRepository;
import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;
import com.fastlog.rastreamento.presentation.dto.response.RespostaCadastrarEncomendaDto;
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
class CadastrarEncomendaTest {

    @Mock
    private EncomendaRepository repository;
    @Spy
    private EncomendaMapper mapper = new EncomendaMapperImpl();
    @InjectMocks
    private CadastrarEncomenda service;
    @Captor
    private ArgumentCaptor<EncomendaEntity> encomendaEntityCaptor;

    @Test
    void deve_cadastrar_encomenda_corretamente() {
        final CadastrarEncomendaDto dadosEncomendaMock = CadastrarEncomendaFactory.cadastrarEncomendaDtoValido();
        final EncomendaEntity encomendaEntityMock = EncomendaEntityFactory.encomendaValida();

        when(repository.save(any(EncomendaEntity.class))).thenReturn(encomendaEntityMock);

        final RespostaCadastrarEncomendaDto respostaService = service.cadastarEncomenda(dadosEncomendaMock);

        verify(repository, times(1)).save(any(EncomendaEntity.class));
        verify(repository).save(encomendaEntityCaptor.capture());

        final EncomendaEntity encomendaEntityCapturado = encomendaEntityCaptor.getValue();

        assertEquals(dadosEncomendaMock.destino(), encomendaEntityCapturado.getDestino());
        assertEquals(dadosEncomendaMock.nome(), encomendaEntityCapturado.getNome());
        assertEquals(dadosEncomendaMock.origem(), encomendaEntityCapturado.getOrigem());
        assertEquals(dadosEncomendaMock.status().cidade(), encomendaEntityCapturado.getStatus().getCidade());
        assertEquals(dadosEncomendaMock.status().pais(), encomendaEntityCapturado.getStatus().getPais());
        assertEquals(dadosEncomendaMock.status().descricao(), encomendaEntityCapturado.getStatus().getDescricao());

        assertEquals(dadosEncomendaMock.nome(), respostaService.nome());
        assertEquals(dadosEncomendaMock.origem(), respostaService.origem());
        assertEquals(dadosEncomendaMock.destino(), respostaService.destino());
        assertEquals(dadosEncomendaMock.status().cidade(), respostaService.status().cidade());
        assertEquals(dadosEncomendaMock.status().pais(), respostaService.status().pais());
        assertEquals(dadosEncomendaMock.status().descricao(), respostaService.status().descricao());
        assertEquals(encomendaEntityMock.getId(), respostaService.id());
        assertEquals(encomendaEntityMock.getStatus().getDataCriacao(), respostaService.status().dataCriacao());
    }
}