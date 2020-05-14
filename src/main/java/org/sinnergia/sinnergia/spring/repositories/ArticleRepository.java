package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ArticleRepository extends ReactiveSortingRepository<Article, String> {
}
