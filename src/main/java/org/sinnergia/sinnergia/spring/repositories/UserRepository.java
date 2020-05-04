package org.sinnergia.sinnergia.spring.repositories;

import org.sinnergia.sinnergia.spring.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, String> {

}
