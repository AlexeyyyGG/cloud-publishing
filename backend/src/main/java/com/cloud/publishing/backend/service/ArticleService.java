package com.cloud.publishing.backend.service;

import com.cloud.publishing.common.dto.ArticleDTO;
import com.cloud.publishing.model.Article;
import java.util.List;

/**
 * Service interface for managing articles. Provides operations for creating, updating, retrieving
 * and deleting articles.
 */
public interface ArticleService {
    /**
     * Creates a new article.
     *
     * @param request {@link ArticleDTO} containing article data
     * @return created {@link Article}
     */
    Article add(ArticleDTO request, Integer authorId);

    /**
     * Updates an existing article.
     *
     * @param id      identifier of the article to update
     * @param request {@link ArticleDTO} containing updated article data
     * @return updated {@link Article}
     */
    Article update(int id, ArticleDTO request, Integer userId);

    /**
     * Returns article by id.
     *
     * @param id identifier of the article
     * @return {@link Article}
     */
    Article get(int id);

    /**
     * Returns list of all articles.
     *
     * @return list of {@link Article}
     */
    List<Article> getAll(Integer authorId, boolean isChiefEditor);

    /**
     * Deletes article by id.
     *
     * @param id identifier of the article to delete
     */
    void delete(int id, Integer currentUserId);
}