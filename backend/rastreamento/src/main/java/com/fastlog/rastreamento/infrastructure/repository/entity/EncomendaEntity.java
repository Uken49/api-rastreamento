package com.fastlog.rastreamento.infrastructure.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Getter
@Table(name = "encomenda")
public class EncomendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String nome;
    String origem;
    String destino;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_fk", referencedColumnName = "id")
    StatusEntity status;

    @Builder(toBuilder = true)
    public EncomendaEntity(
            UUID id,
            String nome,
            String origem,
            String destino,
            StatusEntity status
    ) {
        this.id = id;
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
        this.status = status;
    }
}
