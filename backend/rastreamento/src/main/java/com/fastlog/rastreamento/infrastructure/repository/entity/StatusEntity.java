package com.fastlog.rastreamento.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Getter
@Table(name = "status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String descricao;
    String pais;
    String cidade;
    @CreationTimestamp
    @Column(name = "data_criacao")
    LocalDateTime dataCriacao;

    @Builder
    public StatusEntity() {

    }
}
