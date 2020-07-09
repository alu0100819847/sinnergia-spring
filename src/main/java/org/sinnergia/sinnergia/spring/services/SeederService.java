package org.sinnergia.sinnergia.spring.services;

import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SeederService {

    private UserRepository userRepository;

    @Value("${sinnergia.admin.email}")
    private String adminEmail;

    @Value("${sinnergia.admin.password}")
    private String adminPassword;
    @Autowired
    public SeederService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        this.initialize();
    }



    public void initialize() {
        this.userRepository.findOneByEmail(adminEmail).hasElement();
        if (!this.userRepository.findOneByEmail(adminEmail).hasElement().block()) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(adminPassword);
            user.setRoles(new Role[]{Role.CUSTOMER, Role.ADMIN});
            this.userRepository.save(user).subscribe();
        }
    }

}
