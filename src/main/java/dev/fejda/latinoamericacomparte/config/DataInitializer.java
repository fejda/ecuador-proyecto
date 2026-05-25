package dev.fejda.latinoamericacomparte.config;

import dev.fejda.latinoamericacomparte.model.entity.Administrator;
import dev.fejda.latinoamericacomparte.model.repository.AdministratorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final AdministratorRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AdministratorRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            // Verificar si ya existe un administrador
            if (adminRepository.findByEmail("frank290307@fundacion.com").isEmpty()) {
                Administrator admin = new Administrator();
                admin.setName("Administrador");
                admin.setEmail("frank290307@fundacion.com");
                admin.setPassword(passwordEncoder.encode("290307"));
                adminRepository.save(admin);

                System.out.println("====================================");
                System.out.println("✅ ADMIN CREADO AUTOMÁTICAMENTE");
                System.out.println("📧 Email: frank290307@fundacion.com");
                System.out.println("🔑 Password: 290307");
                System.out.println("====================================");
            } else {
                System.out.println("====================================");
                System.out.println("ℹ️ El administrador ya existe");
                System.out.println("📧 Email: frank290307@fundacion.com");
                System.out.println("====================================");
            }
        };
    }
}