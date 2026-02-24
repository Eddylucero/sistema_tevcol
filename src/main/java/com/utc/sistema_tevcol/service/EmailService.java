package com.utc.sistema_tevcol.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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

        // Diseño de la tarjeta estilizada
        String htmlContent = "<div style='background-color: #f4f4f4; padding: 40px; font-family: sans-serif;'>" +
                "<table width='100%' border='0' cellspacing='0' cellpadding='0'>" +
                "<tr>" +
                "<td align='center'>" +
                "<div style='background-color: #ffffff; max-width: 500px; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.1); border: 1px solid #e0e0e0;'>"
                +
                // Cabecera con Icono/Color
                "<div style='background-color: #2c3e50; padding: 25px; text-align: center;'>" +
                "<span style='font-size: 50px;'>🔐</span>" +
                "<h2 style='color: #ffffff; margin: 10px 0 0 0; font-size: 22px;'>Recuperación de Contraseña</h2>" +
                "</div>" +
                // Cuerpo de la tarjeta
                "<div style='padding: 30px; text-align: center; color: #333333;'>" +
                "<p style='font-size: 16px; line-height: 1.6;'>Hola,</p>" +
                "<p style='font-size: 16px; line-height: 1.6;'>Has solicitado restablecer tu contraseña para acceder al <strong>Sistema TEVCOL</strong>.</p>"
                +
                "<div style='margin: 30px 0;'>" +
                "<a href='" + enlace
                + "' style='background-color: #3498db; color: #ffffff; padding: 15px 25px; border-radius: 6px; text-decoration: none; font-weight: bold; font-size: 16px; display: inline-block;'>Restablecer mi contraseña</a>"
                +
                "</div>" +
                "<p style='font-size: 14px; color: #7f8c8d;'>Este enlace tiene una validez de <strong>5 minutos</strong>.</p>"
                +
                "<hr style='border: 0; border-top: 1px solid #eeeeee; margin: 25px 0;'>" +
                "<p style='font-size: 12px; color: #bdc3c7;'>Si no solicitaste este cambio, puedes ignorar este correo de forma segura.</p>"
                +
                "</div>" +
                // Pie de página
                "<div style='background-color: #f9f9f9; padding: 15px; text-align: center; border-top: 1px solid #eeeeee;'>"
                +
                "<p style='font-size: 12px; color: #95a5a6; margin: 0;'>© 2026 TEVCOL - Seguridad y Logística</p>" +
                "</div>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</div>";

        Map<String, Object> body = Map.of(
                "sender", Map.of(
                        "name", "Sistema TEVCOL",
                        "email", "sistemaseddy8@gmail.com"),
                "to", List.of(
                        Map.of("email", destino)),
                "subject", "Recuperación de contraseña - TEVCOL",
                "htmlContent", htmlContent);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("=== BREVO RESPONSE ===");
            System.out.println("Status: " + response.getStatusCode());
        } catch (HttpStatusCodeException ex) {
            System.out.println("=== BREVO ERROR ===");
            System.out.println("Status: " + ex.getStatusCode());
            System.out.println("Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Error enviando correo con Brevo");
        }
    }
}