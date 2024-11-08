package com.fastlog.rastreamento.infrastructure.repository;

import com.fastlog.rastreamento.infrastructure.repository.entity.StatusEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {
}
