package org.sinnergia.sinnergia.spring.services;

import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserDetailsImplementationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findOneByEmail(email).switchIfEmpty(Mono.error(new UsernameNotFoundException("user not found. " + email))).block();
        return new UserDetailImplementation(user.getEmail(),user.getPassword() ,user.getRoles());
    }


}
