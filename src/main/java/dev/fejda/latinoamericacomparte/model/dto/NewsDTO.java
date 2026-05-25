package dev.fejda.latinoamericacomparte.model.dto;

import dev.fejda.latinoamericacomparte.model.entity.News;

public record NewsDTO(
        String title,
        String summary,
        String content,
        String mainImageUrl,
        String author,
        String status
) {
    public News toEntity() {
        News news = new News();
        news.setTitle(title);
        news.setSummary(summary);
        news.setContent(content);
        news.setMainImageUrl(mainImageUrl);
        news.setAuthor(author);
        news.setStatus(status != null ? status : "DRAFT");
        return news;
    }
}