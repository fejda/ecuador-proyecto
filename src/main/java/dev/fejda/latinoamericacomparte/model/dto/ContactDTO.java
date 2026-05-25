package dev.fejda.latinoamericacomparte.model.dto;

import dev.fejda.latinoamericacomparte.model.constant.Purpose;
import dev.fejda.latinoamericacomparte.model.entity.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record ContactDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9]{9,10}$", message = "El teléfono debe tener 9 o 10 dígitos (Ejemplo: 0999999999)")
        String telephone,

        Purpose purpose,

        Date creationDate
) {
    public Contact toEntity() {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setTelephone(telephone);
        contact.setPurpose(purpose);
        contact.setCreationDate(creationDate != null ? creationDate : new Date());
        return contact;
    }
}