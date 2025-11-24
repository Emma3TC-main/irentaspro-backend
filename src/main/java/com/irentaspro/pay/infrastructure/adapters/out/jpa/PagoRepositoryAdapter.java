package com.irentaspro.pay.infrastructure.adapters.out.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.EstadoPago;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.infrastructure.entity.ComprobanteFiscalEntity;
import com.irentaspro.pay.infrastructure.entity.PagoEntity;
import com.irentaspro.pay.infrastructure.repository.JpaPagoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagoRepositoryAdapter implements PagoRepositorio {

    private final JpaPagoRepository jpaRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Pago guardar(Pago pago) {
        ComprobanteFiscalEntity cfEntity = null;
        if (pago.getComprobanteFiscal() != null) {
            cfEntity = ComprobanteFiscalEntity.builder()
                    .id(pago.getComprobanteFiscal().getId())
                    .tipo(pago.getComprobanteFiscal().getTipo())
                    .xml(pago.getComprobanteFiscal().getXml())
                    .ticketSUNAT(pago.getComprobanteFiscal().getTicketSUNAT())
                    .build();
        }

        PagoEntity entity = PagoEntity.builder()
                .id(pago.getId() != null ? pago.getId() : UUID.randomUUID())
                .contratoId(pago.getContratoId())
                .usuarioId(pago.getUsuarioId())
                .monto(pago.getMonto().valor())
                .moneda(pago.getMonto().moneda())
                .metodo(pago.getMetodo())
                .tipoPago(pago.getTipoPago())
                .estado(pago.getEstado().name())
                .referenciaExterna(pago.getReferenciaExterna())
                .comprobanteFiscal(cfEntity)
                .build();

        PagoEntity saved = jpaRepo.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Pago> buscarPorId(UUID id) {
        return jpaRepo.findById(id).map(this::mapToDomain);
    }

    @Override
    public void eliminar(UUID id) {
        if (jpaRepo.existsById(id)) {
            jpaRepo.deleteById(id);
        }
    }

    @Override
    public List<Pago> buscarTodos() {
        return jpaRepo.findAll().stream().map(this::mapToDomain).toList();
    }

    @Override
    public Optional<Pago> buscarPorReferenciaExterna(String ref) {
        return jpaRepo.findByReferenciaExterna(ref).map(this::mapToDomain);
    }


    private Pago mapToDomain(PagoEntity e) {
        ComprobanteFiscal cf = null;
        if (e.getComprobanteFiscal() != null) {
            cf = new ComprobanteFiscal(
                    e.getComprobanteFiscal().getId(),
                    e.getComprobanteFiscal().getTipo(),
                    e.getComprobanteFiscal().getXml(),
                    e.getComprobanteFiscal().getTicketSUNAT());
        }

        EstadoPago estadoPago = EstadoPago.valueOf(e.getEstado());

        Pago pago = new Pago(
                e.getId(),
                e.getContratoId(),
                e.getUsuarioId(),
                new Monto(e.getMonto(), e.getMoneda()),
                e.getMetodo(),
                e.getTipoPago(),
                estadoPago,
                e.getReferenciaExterna());

        if (cf != null) {
            pago.setComprobanteFiscal(cf);
        }

        return pago;
    }
}
