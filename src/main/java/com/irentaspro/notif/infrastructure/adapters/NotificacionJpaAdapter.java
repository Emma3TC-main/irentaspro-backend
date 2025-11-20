package com.irentaspro.notif.infrastructure.adapters;

import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.repository.NotificacionRepository;
import com.irentaspro.notif.infrastructure.persistence.NotificacionJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class NotificacionJpaAdapter implements NotificacionRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Notificacion guardar(Notificacion n) {
        NotificacionJpaEntity jpa = new NotificacionJpaEntity();
        jpa.setId(n.getId());
        jpa.setDestinatario(n.destinatario());
        jpa.setAsunto(n.asunto());
        jpa.setMensaje(n.mensaje());
        jpa.setTipo(n.tipo());
        jpa.setEstado(n.estado());
        em.merge(jpa);
        return n;
    }

    @Override
    public Optional<Notificacion> buscarPorId(UUID id) {
        NotificacionJpaEntity jpa = em.find(NotificacionJpaEntity.class, id);
        if (jpa == null)
            return Optional.empty();
        return Optional.of(map(jpa));
    }

    @Override
    public List<Notificacion> listarPendientes() {
        return em
                .createQuery("SELECT n FROM NotificacionJpaEntity n WHERE n.estado = 'PENDIENTE'",
                        NotificacionJpaEntity.class)
                .getResultList().stream()
                .map(this::map)
                .toList();
    }

    private Notificacion map(NotificacionJpaEntity j) {
        Notificacion n = new Notificacion(j.getId(), j.getDestinatario(), j.getAsunto(), j.getMensaje(), j.getTipo());
        if (!Objects.equals(j.getEstado(), "PENDIENTE")) {
            if (j.getEstado().equals("ENVIADA"))
                n.marcarEnviada();
            else
                n.marcarError();
        }
        return n;
    }
}