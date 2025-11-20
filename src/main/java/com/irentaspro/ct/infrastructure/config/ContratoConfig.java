package com.irentaspro.ct.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.irentaspro.ct.domain.services.ContratoService;
import com.irentaspro.ct.domain.services.IFirmaAdapter;

@Configuration
public class ContratoConfig {

    @Bean
    public ContratoService contratoService(IFirmaAdapter firmaAdapter) {
        return new ContratoService(firmaAdapter);
    }
}
