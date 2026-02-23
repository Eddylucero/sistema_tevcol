package com.utc.sistema_tevcol.service;

import com.utc.sistema_tevcol.entity.Usuario;
import com.utc.sistema_tevcol.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe el admin para no duplicarlo cada vez que inicies
        if (usuarioRepo.findByEmail("luceroeddy8@gmail.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("luceroeddy8@gmail.com");
            // Encriptamos la contraseña "admin123"
            admin.setPassword(passwordEncoder.encode("123456"));

            usuarioRepo.save(admin);
            System.out.println(">>> Usuario administrador creado exitosamente (luceroeddy8@gmail.com / 123456)");
        }
    }
}