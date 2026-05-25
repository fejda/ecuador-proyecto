package dev.fejda.latinoamericacomparte.model.dto;

import dev.fejda.latinoamericacomparte.model.entity.Administrator;

public record AdministratorDTO(
        String name,
        String email,
        String password
) {
    public Administrator toEntity() {
        Administrator administrator = new Administrator();
        administrator.setName(name);
        administrator.setEmail(email);
        administrator.setPassword(password);
        return administrator;
    }
}