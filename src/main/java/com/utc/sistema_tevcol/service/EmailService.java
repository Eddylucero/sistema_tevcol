package com.utc.sistema_tevcol.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${app.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarCorreoRecuperacion(String destino, String token) {

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
                  "htmlContent": "<h3>Recuperación de contraseña</h3>
                                  <p>Hola, solicitaste restablecer tu contraseña.</p>
                                  <p><a href='%s'>Haz clic aquí para cambiarla</a></p>
                                  <p>Este enlace expira en 5 minutos.</p>
                                  <p>Si no fuiste tú, ignora este mensaje.</p>"
                }
                """.formatted(destino, enlace);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("=== BREVO RESPONSE ===");
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());

        } catch (HttpStatusCodeException ex) {

            System.out.println("=== BREVO ERROR ===");
            System.out.println("Status: " + ex.getStatusCode());
            System.out.println("Body: " + ex.getResponseBodyAsString());

            throw new RuntimeException("Error enviando correo con Brevo");
        }
    }
}