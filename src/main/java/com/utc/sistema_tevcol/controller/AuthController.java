package com.utc.sistema_tevcol.controller;

import com.utc.sistema_tevcol.entity.Usuario;
import com.utc.sistema_tevcol.repository.UsuarioRepository;
import com.utc.sistema_tevcol.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            usuarioRepo.save(u);
            emailService.enviarCorreoRecuperacion(u.getEmail(), token);
            return "redirect:/auth/olvido?success";
        } else {
            return "redirect:/auth/olvido?errorEmail";
        }
    }

    @GetMapping("/restablecer")
    public String mostrarRestablecer(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "auth/restablecer";
    }

    @PostMapping("/restablecer")
    public String procesarRestablecer(@RequestParam("token") String token,
            @RequestParam("password") String password) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByResetToken(token);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            u.setPassword(encoder.encode(password));
            u.setResetToken(null);
            usuarioRepo.save(u);
            return "redirect:/auth/login?resetSuccess";
        }
        return "redirect:/auth/login?resetError";
    }
}