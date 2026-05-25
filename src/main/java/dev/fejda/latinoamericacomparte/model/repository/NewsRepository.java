package dev.fejda.latinoamericacomparte.model.repository;

import dev.fejda.latinoamericacomparte.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByStatusOrderByPublicationDateDesc(String status);
    List<News> findAllByOrderByPublicationDateDesc();
}