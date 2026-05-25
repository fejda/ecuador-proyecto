package dev.fejda.latinoamericacomparte.model.service;

import dev.fejda.latinoamericacomparte.model.dto.AdministratorDTO;
import dev.fejda.latinoamericacomparte.model.entity.Administrator;
import dev.fejda.latinoamericacomparte.model.repository.AdministratorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository repository;

    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    public List<Administrator> findAllAdministrators() {
        return repository.findAll();
    }

    public Optional<Administrator> findAdministratorById(Long id) {
        return repository.findById(id);
    }


    public Optional<Administrator> findAdministratorByEmail(String email) {
        return repository.findByEmail(email);
    }


    public void createAdministrator(AdministratorDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("Ya existe un administrador con ese email");
        }
        repository.save(dto.toEntity());
    }


    public void updateAdministrator(Long id, AdministratorDTO request) throws EntityNotFoundException {
        Administrator administrator = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Administrator with id " + id + " not found"));

        administrator.setName(request.name());
        administrator.setEmail(request.email());
        administrator.setPassword(request.password());

        repository.save(administrator);
    }


    public void deleteAdministrator(Long id) throws EntityNotFoundException {
        Administrator administrator = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Administrator with id " + id + " not found"));
        repository.delete(administrator);
    }
}