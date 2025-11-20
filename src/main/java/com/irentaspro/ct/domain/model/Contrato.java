package com.irentaspro.ct.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.AggregateRoot;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.application.dto.PagoRealizadoDTO;
import com.irentaspro.ct.domain.model.events.CalendarioPagosGenerado;
import com.irentaspro.ct.domain.model.events.ContratoFirmado;
import com.irentaspro.ct.domain.model.events.PagoRegistrado;
import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;
import com.irentaspro.ct.domain.model.valueobjects.EstadoContrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

public class Contrato extends AggregateRoot {

    private final UUID propiedadId;
    private final UUID propietarioId;
    private final UUID inquilinoId;
    private PeriodoContrato periodo;
    private final Monto monto; // monto mensual
    private EstadoContrato estado;
    private final List<Clausula> clausulas;
    private FirmaDigital firmaDigital;
    private BigDecimal montoPendiente; // saldo total

    private List<CuotaContrato> calendario = new ArrayList<>();

    public Contrato(UUID propiedadId,
            UUID propietarioId,
            UUID inquilinoId,
            PeriodoContrato periodo,
            Monto monto,
            List<Clausula> clausulas) {

        this.id = UUID.randomUUID();

        if (propietarioId == null || inquilinoId == null)
            throw new IllegalArgumentException("Propietario e inquilino son obligatorios");

        if (propietarioId.equals(inquilinoId))
            throw new IllegalArgumentException("El propietario no puede ser el inquilino.");

        this.propiedadId = propiedadId;
        this.propietarioId = propietarioId;
        this.inquilinoId = inquilinoId;
        this.periodo = periodo;
        this.monto = monto;
        this.clausulas = clausulas;

        this.montoPendiente = calcularMontoTotal(periodo, monto);
        this.estado = EstadoContrato.BORRADOR;

        validarInvariantes();
    }

    private BigDecimal calcularMontoTotal(PeriodoContrato periodo, Monto montoMensual) {
        long meses = periodo.mesesEntre();
        return montoMensual.valor().multiply(BigDecimal.valueOf(meses));
    }

    // ==========================================================
    // MÉTODOS DE NEGOCIO
    // ==========================================================

    public List<CuotaContrato> generarCalendarioPagos() {
        if (!estado.es(EstadoContrato.FIRMADO) && !estado.es(EstadoContrato.RENOVADO))
            throw new IllegalStateException("Solo contratos firmados o renovados pueden generar calendario.");

        var cursor = periodo.getInicio().withDayOfMonth(1);
        var fin = periodo.getFin();

        List<CuotaContrato> cuotas = new ArrayList<>();
        int numero = 1;

        while (!cursor.isAfter(fin)) {
            cuotas.add(new CuotaContrato(numero++, cursor, monto.valor()));
            cursor = cursor.plusMonths(1);
        }

        this.calendario = cuotas;

        agregarEventoDominio(new CalendarioPagosGenerado(this.getId(), cuotas));

        return cuotas;
    }

    public void registrarPago(BigDecimal montoPagado) {
        if (montoPagado == null || montoPagado.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El monto del pago es inválido");

        if (this.montoPendiente.compareTo(montoPagado) < 0)
            throw new IllegalStateException("El pago excede el saldo pendiente");

        this.montoPendiente = this.montoPendiente.subtract(montoPagado);

        BigDecimal restante = montoPagado;
        for (CuotaContrato cuota : calendario) {
            if (!cuota.isPagado() && restante.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal montoCuota = cuota.getMonto();
                if (restante.compareTo(montoCuota) >= 0) {
                    cuota.marcarComoPagada();
                    restante = restante.subtract(montoCuota);
                } else {
                    break; // pago parcial
                }
            }
        }

        agregarEventoDominio(new PagoRegistrado(this.getId(), montoPagado));
    }

    public void firmar(FirmaDigital firma) {
        if (!estado.es(EstadoContrato.BORRADOR))
            throw new IllegalStateException("El contrato no puede ser firmado en su estado actual.");

        this.firmaDigital = firma;
        this.estado = EstadoContrato.FIRMADO;

        agregarEventoDominio(new ContratoFirmado(this.getId()));
    }

    public void renovar(PeriodoContrato nuevoPeriodo) {
        if (!estado.es(EstadoContrato.FINALIZADO))
            throw new IllegalStateException("Solo se pueden renovar contratos finalizados.");

        this.periodo = nuevoPeriodo;
        this.estado = EstadoContrato.RENOVADO;
    }

    public void finalizar() {
        if (estado.es(EstadoContrato.FIRMADO) || estado.es(EstadoContrato.RENOVADO)) {
            if (this.montoPendiente.compareTo(BigDecimal.ZERO) > 0)
                throw new IllegalStateException("No se puede finalizar con pagos pendientes.");
            this.estado = EstadoContrato.FINALIZADO;
        } else {
            throw new IllegalStateException("Solo se pueden finalizar contratos firmados o renovados.");
        }
    }

    public void restaurarEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public void restaurarMontoPendiente(BigDecimal valor) {
        this.montoPendiente = valor != null ? valor : BigDecimal.ZERO;
    }

    public void restaurarFirma(FirmaDigital firma) {
        this.firmaDigital = firma;
    }

    public void restaurarId(UUID id) {
        this.id = id;
    }

    public void restaurarCalendario(List<CuotaContrato> calendario) {
        this.calendario = calendario != null ? calendario : new ArrayList<>();
    }

    @Override
    public void validarInvariantes() {
        if (propiedadId == null || inquilinoId == null || propietarioId == null)
            throw new IllegalArgumentException("El contrato debe tener propiedad, propietario e inquilino definidos.");

        if (periodo == null || monto == null || clausulas == null || clausulas.isEmpty())
            throw new IllegalArgumentException("El contrato debe tener periodo, monto y cláusulas válidas.");
    }

    // ==========================================================
    // GETTERS
    // ==========================================================
    public UUID getPropiedadId() {
        return propiedadId;
    }

    public UUID getPropietarioId() {
        return propietarioId;
    }

    public UUID getInquilinoId() {
        return inquilinoId;
    }

    public PeriodoContrato getPeriodo() {
        return periodo;
    }

    public Monto getMonto() {
        return monto;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public List<Clausula> getClausulas() {
        return clausulas;
    }

    public FirmaDigital getFirmaDigital() {
        return firmaDigital;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public List<CuotaContrato> getCalendario() {
        return calendario;
    }
}