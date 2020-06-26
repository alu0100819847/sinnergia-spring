package org.sinnergia.sinnergia.spring.services;

import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsImplementationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email).block();
        if(user == null){
            throw new UsernameNotFoundException("user not found. " + email);
        }
        return new UserDetailImplementation(user.getEmail(),user.getPassword() ,user.getRoles());
    }


}
