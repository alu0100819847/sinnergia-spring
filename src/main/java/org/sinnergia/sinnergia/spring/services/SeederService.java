package org.sinnergia.sinnergia.spring.services;

import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.exceptions.ConflictException;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SeederService {


    private UserRepository userRepository;

    @Autowired
    public SeederService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        this.initialize();
    }



    public void initialize() {
        if (this.userRepository.findOneByEmail("adminuser@sinnergia.org").hasElement().block() == null || !this.userRepository.findOneByEmail("adminuser@sinnergia.org").hasElement().block()) {
            User user = new User();
            user.setEmail("adminuser@sinnergia.org");
            user.setPassword("adminuser");
            user.setRoles(new Role[]{Role.CUSTOMER, Role.ADMIN});

            this.userRepository.save(user).subscribe();
        }
    }

}
