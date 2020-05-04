package org.sinnergia.sinnergia.spring.repositoriesTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sinnergia.sinnergia.spring.entities.Users;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users createUser(String name, String email, String password){
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    @Test
    public void createUsers(){
        Users user = createUser("tomcat", "tomcat@gmail.com", "tomcat");
        this.userRepository.save(user);
        List<Users> users = new ArrayList<Users>();
        this.userRepository.findAll().forEach(users::add);
        assertEquals(1, users.size());

    }

    @Test
    public void getUsers() {
        Users user = createUser("tomcat", "tomcat@gmail.com", "tomcat");
        this.userRepository.save(user);
        Optional<Users> userDB = userRepository.findById(user.getId());
        assertTrue(userDB.isPresent());
        assertEquals(user.getName(), userDB.get().getName());
        assertEquals("tomcat", userDB.get().getName());
    }

    @Test
    public void createRepeatedUser(){
        try{
            Users user = createUser("tomcat", "tomcat@gmail.com", "tomcat");
            Users repeatedUser = createUser("tomcat", "tomcat@gmail.com", "tomcat");
            this.userRepository.save(user);
            this.userRepository.save(repeatedUser);
            List<Users> users = new ArrayList<Users>();
            this.userRepository.findAll().forEach(users::add);
        } catch (DataIntegrityViolationException error){
            assertNotNull(error);
        }

    }

}



