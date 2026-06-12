package com.cloud.publishing.backend.repository;

import com.cloud.publishing.model.Article;
import java.util.List;

public interface ArticleRepository extends IRepository<Article, Integer> {
    List<Article> getAll();

    List<Article> getByAuthorId(Integer id);
}