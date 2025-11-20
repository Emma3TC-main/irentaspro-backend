package com.irentaspro.common.infrastructure;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.irentaspro.common.domain.model.DomainEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publish(List<DomainEvent> eventos) {
        if (eventos == null || eventos.isEmpty())
            return;
        eventos.forEach(publisher::publishEvent);
    }
}
