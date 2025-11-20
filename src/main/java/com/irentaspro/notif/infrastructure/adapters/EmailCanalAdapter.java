package com.irentaspro.notif.infrastructure.adapters;

import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.service.CanalEnvio;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailCanalAdapter implements CanalEnvio {

    private final JavaMailSender mailSender;

    public EmailCanalAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(Notificacion notif) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(notif.destinatario());
        msg.setSubject(notif.asunto());
        msg.setText(notif.mensaje());
        mailSender.send(msg);
    }
}