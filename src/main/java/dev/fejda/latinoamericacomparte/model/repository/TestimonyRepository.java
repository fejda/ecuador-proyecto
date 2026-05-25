package dev.fejda.latinoamericacomparte.model.repository;

import dev.fejda.latinoamericacomparte.model.constant.Status;
import dev.fejda.latinoamericacomparte.model.entity.Testimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestimonyRepository extends JpaRepository<Testimony, Long> {
    List<Testimony> findByStatus(Status status);
    List<Testimony> findByStatusOrderByCreationDateDesc(Status status);

}