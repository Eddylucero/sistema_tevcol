package com.utc.sistema_tevcol.config;

import com.utc.sistema_tevcol.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UsuarioRepository usuarioRepo;

    public CustomAuthenticationFailureHandler(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // Obtenemos el correo que el usuario intentó ingresar
        String email = request.getParameter("username");

        // Verificamos individualmente
        if (usuarioRepo.findByEmail(email).isEmpty()) {
            // El correo no existe
            setDefaultFailureUrl("/auth/login?errorEmail");
        } else {
            // El correo existe, entonces lo que falló fue la contraseña
            setDefaultFailureUrl("/auth/login?errorPass");
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}