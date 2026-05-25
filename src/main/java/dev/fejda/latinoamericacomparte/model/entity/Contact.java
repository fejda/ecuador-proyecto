package dev.fejda.latinoamericacomparte.model.entity;

import dev.fejda.latinoamericacomparte.model.constant.Purpose;
import dev.fejda.latinoamericacomparte.model.dto.ContactDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "contact_request")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 16)
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "creation_Date", nullable = false)
    private Date creationDate;

    public Contact() { }

    public Long getContactID() { return contactID; }
    public void setContactID(Long contactID) { this.contactID = contactID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public Purpose getPurpose() { return purpose; }
    public void setPurpose(Purpose purpose) { this.purpose = purpose; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public void replaceFieldWith(ContactDTO dto){
        setName(dto.name());
        setEmail(dto.email());
        setTelephone(dto.telephone());
        setPurpose(dto.purpose());
        setCreationDate(new Date());
    }
}