package com.fastlog.rastreamento.infrastructure.repository;

import com.fastlog.rastreamento.infrastructure.repository.entity.EncomendaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncomendaRepository extends JpaRepository<EncomendaEntity, UUID> {
}
