package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.documents.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveSortingRepository<User, String> {

    Flux<User> findAllByName(String name);
    Mono<User> findOneByEmail(String email);


}
