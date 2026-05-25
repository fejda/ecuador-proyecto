package dev.fejda.latinoamericacomparte.model.entity;

import dev.fejda.latinoamericacomparte.model.dto.AdministratorDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id")
    private Long administratorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    public Administrator() {
    }


    public Administrator(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void replaceFieldWith(AdministratorDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.password = dto.password();
    }
}