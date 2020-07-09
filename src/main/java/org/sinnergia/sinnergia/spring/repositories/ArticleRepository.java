package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.Category;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleRepository extends ReactiveSortingRepository<Article, String> {
    Flux<Article> findAllByName(String name);
    Flux<Article> findAllByCategory(Mono<Category> category);
}
