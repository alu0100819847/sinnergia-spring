package org.sinnergia.sinnergia.spring.repositories;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.TestConfig;
import org.sinnergia.sinnergia.spring.documents.User;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;


@TestConfig
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    @BeforeEach
    void removeAll(){
        StepVerifier
                .create(this.userRepository.deleteAll())
                .expectComplete()
                .verify();
    }

    @Test
    void createUsers(){
        User user = createUser("tomcat", "tomcat@gmail.com", "tomcat");
        StepVerifier
                .create(this.userRepository.save(user))
                .expectNextCount(1)
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userRepository.findAll())
                .expectNextCount(1)
                .expectComplete()
                .verify();

       this.removeUser(user);
    }

    @Test
    void searchByEmail(){
        User user = createUser("tomcat", "tomcat1@gmail.com", "tomcat");
        StepVerifier
                .create(this.userRepository.save(user))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        User user2 = createUser("tomcat2", "tomcat2@gmail.com", "tomca2t");
        StepVerifier
                .create(this.userRepository.save(user2))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        StepVerifier
                .create(this.userRepository.findOneByEmail("tomcat2@gmail.com"))
                .expectNextMatches(searchedUser -> {
                    assertEquals("tomcat2",searchedUser.getName());
                    return true;
                })
                .expectComplete()
                .verify();
        this.removeUser(user);
        this.removeUser(user2);
    }


    @Test
    void searchNotExistedEmail(){
        StepVerifier
                .create(this.userRepository.findOneByEmail("tomcat4@gmail.com"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void searchAllByName(){
        User user = createUser("tomcat", "tomcat5@gmail.com", "tomcat");
        StepVerifier
                .create(this.userRepository.save(user))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        User user2 = createUser("tomcat", "tomcat6@gmail.com", "tomca2t");
        StepVerifier
                .create(this.userRepository.save(user2))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        User user3 = createUser("tomcat12", "tomcat7@gmail.com", "tomca2t");
        StepVerifier
                .create(this.userRepository.save(user3))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        StepVerifier
                .create(this.userRepository.findAllByName("tomcat"))
                .expectNextCount(2)
                .expectComplete()
                .verify();
        this.removeUser(user);
        this.removeUser(user2);
        this.removeUser(user3);
    }

    void removeUser(User user){
        StepVerifier
                .create(this.userRepository.delete(user))
                .expectComplete()
                .verify();
    }


}
