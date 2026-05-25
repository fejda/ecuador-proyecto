package dev.fejda.latinoamericacomparte.model.dto;

import dev.fejda.latinoamericacomparte.model.constant.Status;
import dev.fejda.latinoamericacomparte.model.entity.Testimony;

public record PublicTestimonyDTO(
        String name,
        String photoURL,
        String instagramURL,
        String facebookURL,
        String contactEmail

) {
    public Testimony toEntity() {
        Testimony testimony = new Testimony();
        testimony.setName(name);
        testimony.setPhotoURL(photoURL);
        testimony.setInstagramURL(instagramURL);
        testimony.setFacebookURL(facebookURL);
        testimony.setContactEmail(contactEmail);
        testimony.setStatus(Status.STATUS_DRAFT);
        return testimony;
    }
}