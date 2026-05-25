package dev.fejda.latinoamericacomparte.model.service;

import dev.fejda.latinoamericacomparte.model.entity.Administrator;
import dev.fejda.latinoamericacomparte.model.repository.AdministratorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministratorRepository adminRepository;

    public CustomUserDetailsService(AdministratorRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrator admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

        return new User(admin.getEmail(), admin.getPassword(), Collections.emptyList());
    }
}