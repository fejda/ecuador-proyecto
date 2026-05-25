package dev.fejda.latinoamericacomparte.model.service;

import dev.fejda.latinoamericacomparte.model.dto.ContactDTO;
import dev.fejda.latinoamericacomparte.model.entity.Contact;
import dev.fejda.latinoamericacomparte.model.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository){
        this.repository = repository;
    }

    public List<Contact> findAllContact(){
        return repository.findAll();
    }

    public void createContact(ContactDTO contact){
        repository.save(contact.toEntity());
    }

    public boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public Optional<Contact> findContactByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Contact> findContactById(Long id) {
        return repository.findById(id);
    }

    public void updateContact(Long id, ContactDTO request) throws EntityNotFoundException{
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact with id " + id + " not found"));
        contact.replaceFieldWith(request);
        repository.save(contact);
    }

    public void deleteContact(Long id) throws EntityNotFoundException {
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact with id " + id + " not found"));
        repository.delete(contact);
    }
}