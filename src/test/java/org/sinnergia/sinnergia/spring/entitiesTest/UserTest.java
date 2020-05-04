package org.sinnergia.sinnergia.spring.entitiesTest;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.entities.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilder() {
        LocalDateTime registrationDate = LocalDateTime.now();
        Users user = new Users();
        user.setRegistrationDate(registrationDate);
        user.setPassword("vivaElTomcat");
        assertNotEquals("vivaElTomcat", user.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("vivaElTomcat",user.getPassword()));
        assertEquals(registrationDate, user.getRegistrationDate());
    }
}
