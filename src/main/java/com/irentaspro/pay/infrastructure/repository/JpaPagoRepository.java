package com.irentaspro.pay.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irentaspro.pay.infrastructure.entity.PagoEntity;

@Repository
public interface JpaPagoRepository extends JpaRepository<PagoEntity, UUID> {

}
