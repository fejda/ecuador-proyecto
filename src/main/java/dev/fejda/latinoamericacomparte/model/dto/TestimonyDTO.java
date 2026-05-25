package dev.fejda.latinoamericacomparte.model.dto;

import dev.fejda.latinoamericacomparte.model.entity.Testimony;

import java.util.Date;

public record TestimonyDTO(

        String name,
        String photoURL,
        String instagramURL,
        String facebookURL,
        Date creationDate
) {

        public Testimony toEntity() {
            Testimony testimony = new Testimony();

            testimony.setName(name);
            testimony.setPhotoURL(photoURL);
            testimony.setInstagramURL(instagramURL);
            testimony.setFacebookURL(facebookURL);
            testimony.setCreationDate(creationDate != null ? creationDate : new Date());

            return testimony;

        }
}
