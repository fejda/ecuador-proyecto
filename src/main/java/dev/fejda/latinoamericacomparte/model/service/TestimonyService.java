package dev.fejda.latinoamericacomparte.model.service;

import dev.fejda.latinoamericacomparte.model.constant.Status;
import dev.fejda.latinoamericacomparte.model.dto.PublicTestimonyDTO;
import dev.fejda.latinoamericacomparte.model.dto.TestimonyDTO;
import dev.fejda.latinoamericacomparte.model.entity.Testimony;
import dev.fejda.latinoamericacomparte.model.repository.TestimonyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonyService {
    private final TestimonyRepository repository;


    public TestimonyService(TestimonyRepository repository) {
        this.repository = repository;
    }

    public List<Testimony> findAllTestimony() {
        return repository.findAll();
    }

    public List<Testimony> getApprovedTestimonies() {  return repository.findByStatusOrderByCreationDateDesc(Status.STATUS_PUBLISHED);
    }

    public List<Testimony> getPendingTestimonies() {
        return repository.findByStatus(Status.STATUS_DRAFT);
    }

    public Optional<Testimony> findTestimonyById(Long id) {
        return repository.findById(id);
    }

    public void createPublicTestimony(PublicTestimonyDTO dto) {
        repository.save(dto.toEntity());
    }

    public void createTestimony(TestimonyDTO testimonyDTO) {
        Testimony testimony = testimonyDTO.toEntity();
        testimony.setStatus(Status.STATUS_PUBLISHED);
        repository.save(testimony);
    }

    public void approveTestimony(Long id) throws EntityNotFoundException {
        Testimony testimony = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Testimony not found"));
        testimony.setStatus(Status.STATUS_PUBLISHED);
        repository.save(testimony);
    }

    public void rejectTestimony(Long id, String reason) throws EntityNotFoundException {
        Testimony testimony = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Testimony not found"));
        testimony.setStatus(Status.STATUS_DRAFT);
        testimony.setRejectionReason(reason);
        repository.save(testimony);
    }

    public void updateTestimony(Long id, TestimonyDTO request) throws EntityNotFoundException {
        Testimony testimony = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Testimony with id " + id + " not found"));
        testimony.setName(request.name());
        testimony.setPhotoURL(request.photoURL());
        testimony.setInstagramURL(request.instagramURL());
        testimony.setFacebookURL(request.facebookURL());
        repository.save(testimony);
    }

    public void deleteTestimony(Long id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Testimony with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}