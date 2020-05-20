package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface ArticleRepository extends ReactiveSortingRepository<Article, String> {
    Flux<Article> findAllByName(String name);
}
