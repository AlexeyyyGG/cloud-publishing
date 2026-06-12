package com.cloud.publishing.backend.service.implementation;

import static com.cloud.publishing.common.constants.article.ArticleMessage.ACCESS_DENIED_ERROR;
import static com.cloud.publishing.common.constants.article.ArticleMessage.INVALID_CO_AUTHORS_ERROR;
import static com.cloud.publishing.common.constants.article.ArticleMessage.SELF_CO_AUTHOR_ERROR;

import com.cloud.publishing.backend.mapper.ArticleMapper;
import com.cloud.publishing.backend.repository.ArticleRepository;
import com.cloud.publishing.backend.service.ArticleService;
import com.cloud.publishing.backend.service.PublicationService;
import com.cloud.publishing.common.dto.ArticleDTO;
import com.cloud.publishing.model.Article;
import com.cloud.publishing.model.publication.Publication;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;
    private final ArticleMapper mapper;
    private final PublicationService publicationService;

    @Autowired
    public ArticleServiceImpl(
            ArticleRepository repository,
            ArticleMapper mapper,
            PublicationService publicationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.publicationService = publicationService;
    }

    @Override
    public Article add(ArticleDTO request, Integer authorId) {
        validateCoAuthors(request, authorId);
        return repository.add(mapper.toEntity(request, authorId));
    }

    @Override
    public Article update(int id, ArticleDTO request, Integer currentUserId) {
        Article existing = repository.get(id);
        if (!existing.authorId().equals(currentUserId)) {
            throw new AccessDeniedException(ACCESS_DENIED_ERROR);
        }
        validateCoAuthors(request, existing.authorId());
        Article updated = new Article(
                existing.id(),
                request.publicationId(),
                request.categoryId(),
                request.name(),
                request.content(),
                existing.authorId(),
                request.coAuthorsIds()
        );
        repository.update(updated);
        return updated;
    }

    @Override
    public Article get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Article> getAll(Integer authorId, boolean isChiefEditor) {
        if (isChiefEditor) {
            return repository.getAll();
        } else {
            return repository.getByAuthorId(authorId);
        }
    }

    @Override
    public void delete(int id, Integer currentUserId) {
        Article article = repository.get(id);
        if (!article.authorId().equals(currentUserId)) {
            throw new AccessDeniedException(ACCESS_DENIED_ERROR);
        }
        repository.delete(id);
    }

    private void validateCoAuthors(ArticleDTO request, Integer authorId) {
        if (request.coAuthorsIds() == null) {
            return;
        }
        if (request.coAuthorsIds().contains(authorId)) {
            throw new IllegalArgumentException(SELF_CO_AUTHOR_ERROR);
        }
        Publication publication = publicationService.get(request.publicationId());
        if (!publication.journalists().containsAll(request.coAuthorsIds())) {
            throw new IllegalArgumentException(INVALID_CO_AUTHORS_ERROR);
        }
    }
}