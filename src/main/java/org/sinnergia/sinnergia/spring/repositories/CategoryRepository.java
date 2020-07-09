package org.sinnergia.sinnergia.spring.repositories;


import org.sinnergia.sinnergia.spring.documents.Category;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveSortingRepository<Category, String> {
    Mono<Category> findOneByName(String name);
}
