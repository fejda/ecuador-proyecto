package dev.fejda.latinoamericacomparte.model.service;

import dev.fejda.latinoamericacomparte.model.dto.NewsDTO;
import dev.fejda.latinoamericacomparte.model.entity.News;
import dev.fejda.latinoamericacomparte.model.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    public List<News> findAllNews() {
        return repository.findAllByOrderByPublicationDateDesc();
    }

    public List<News> getPublishedNews() {
        return repository.findByStatusOrderByPublicationDateDesc("PUBLISHED");
    }

    public Optional<News> findNewsById(Long id) {
        return repository.findById(id);
    }

    public void createNews(NewsDTO dto) {
        News news = dto.toEntity();
        news.setPublicationDate(new Date());
        repository.save(news);
    }

    public void updateNews(Long id, NewsDTO request) throws EntityNotFoundException {
        News news = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found with id: " + id));
        news.setTitle(request.title());
        news.setSummary(request.summary());
        news.setContent(request.content());
        news.setMainImageUrl(request.mainImageUrl());
        news.setAuthor(request.author());
        news.setStatus(request.status());
        repository.save(news);
    }

    public void deleteNews(Long id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("News not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public void publishNews(Long id) throws EntityNotFoundException {
        News news = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found with id: " + id));
        news.setStatus("PUBLISHED");
        repository.save(news);
    }
}