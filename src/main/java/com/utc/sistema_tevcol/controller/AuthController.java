package com.utc.sistema_tevcol.controller;

import com.utc.sistema_tevcol.entity.Usuario;
import com.utc.sistema_tevcol.repository.UsuarioRepository;
import com.utc.sistema_tevcol.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    public AuthController(UsuarioRepository usuarioRepo, PasswordEncoder encoder, EmailService emailService) {
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/olvido")
    public String mostrarOlvido() {
        return "auth/olvido";
    }

    @PostMapping("/olvido")
    public String procesarOlvido(@RequestParam("email") String email) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            String token = UUID.randomUUID().toString();

            u.setResetToken(token);
            // Establecemos la expiración a 5 minutos desde ahora
            u.setTokenExpiration(LocalDateTime.now().plusMinutes(5));

            usuarioRepo.save(u);
            emailService.enviarCorreoRecuperacion(u.getEmail(), token);

            return "redirect:/auth/olvido?success";
        } else {
            return "redirect:/auth/olvido?errorEmail";
        }
    }

    @GetMapping("/restablecer")
    public String mostrarRestablecer(@RequestParam("token") String token, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByResetToken(token);

        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();

            // Verificamos si el token ha expirado
            if (u.getTokenExpiration() != null && u.getTokenExpiration().isAfter(LocalDateTime.now())) {
                model.addAttribute("token", token);
                return "auth/restablecer";
            }
        }

        // Si no existe el token o ya expiró, mandamos al login con error
        return "redirect:/auth/login?tokenExpirado";
    }

    @PostMapping("/restablecer")
    public String procesarRestablecer(@RequestParam("token") String token,
            @RequestParam("password") String password) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByResetToken(token);

        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();

            // Validación de seguridad extra: verificar tiempo antes de guardar
            if (u.getTokenExpiration() != null && u.getTokenExpiration().isAfter(LocalDateTime.now())) {
                u.setPassword(encoder.encode(password));

                // Limpiamos el token y la expiración para que no se vuelvan a usar
                u.setResetToken(null);
                u.setTokenExpiration(null);

                usuarioRepo.save(u);
                return "redirect:/auth/login?resetSuccess";
            } else {
                return "redirect:/auth/login?tokenExpirado";
            }
        }
        return "redirect:/auth/login?resetError";
    }
}