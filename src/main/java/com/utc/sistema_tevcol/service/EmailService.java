package com.utc.sistema_tevcol.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${app.base-url}")
    private String baseUrl;

    public void enviarCorreoRecuperacion(String destino, String token) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String enlace = baseUrl + "/auth/restablecer?token=" + token;

        String body = """
                {
                  "sender": {
                    "name": "Sistema TEVCOL",
                    "email": "a32ec9001@smtp-brevo.com"
                  },
                  "to": [{
                    "email": "%s"
                  }],
                  "subject": "Recuperación de contraseña - TEVCOL",
                  "htmlContent": "<p>Hola, solicitaste restablecer tu contraseña.</p><p><a href='%s'>Haz clic aquí para cambiarla</a></p><p>Si no fuiste tú, ignora este correo.</p>"
                }
                """
                .formatted(destino, enlace);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}