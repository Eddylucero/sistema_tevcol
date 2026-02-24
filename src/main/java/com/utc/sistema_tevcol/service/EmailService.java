package com.utc.sistema_tevcol.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // 👇 NUEVA LÍNEA - Inyecta la URL base
    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreoRecuperacion(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Recuperación de Contraseña - TEVCOL");

        // ✅ AHORA USA LA URL CORRECTA (Railway o local según el entorno)
        String url = baseUrl + "/auth/restablecer?token=" + token;

        message.setText("Hola, has solicitado restablecer tu contraseña.\n\n" +
                "Haz clic en el siguiente enlace para cambiarla:\n" + url +
                "\n\nSi no fuiste tú, ignora este correo.");

        mailSender.send(message);
    }
}