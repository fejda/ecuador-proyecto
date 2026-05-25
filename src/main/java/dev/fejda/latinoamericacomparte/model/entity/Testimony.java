package dev.fejda.latinoamericacomparte.model.entity;

import dev.fejda.latinoamericacomparte.model.constant.Status;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "testimony")
public class Testimony {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testimony_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "photo_url", length = 2000)
    private String photoURL;

    @Column(name = "instagram_url", length = 2000)
    private String instagramURL;

    @Column(name = "facebook_url", length = 2000)
    private String facebookURL;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "creation_Date", nullable = false)
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.STATUS_DRAFT;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    public Testimony() {
        this.creationDate = new Date();
        this.status = Status.STATUS_DRAFT;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }
    public String getInstagramURL() { return instagramURL; }
    public void setInstagramURL(String instagramURL) { this.instagramURL = instagramURL; }
    public String getFacebookURL() { return facebookURL; }
    public void setFacebookURL(String facebookURL) { this.facebookURL = facebookURL; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }


    public boolean isApproved() {
        return status == Status.STATUS_PUBLISHED;
    }

    public boolean isPending() {
        return status == Status.STATUS_DRAFT;
    }
}