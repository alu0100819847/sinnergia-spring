package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.documents.JWTConfig;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface JWTConfigRepository extends ReactiveSortingRepository<JWTConfig, String> {
}
