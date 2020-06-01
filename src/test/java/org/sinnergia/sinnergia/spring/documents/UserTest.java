package org.sinnergia.sinnergia.spring.documents;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUser() {
        LocalDateTime registrationDate = LocalDateTime.now();
        User user = new User();
        user.setRegistrationDate(registrationDate);
        user.setPassword("vivaElTomcat");
        assertNotEquals("vivaElTomcat", user.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("vivaElTomcat",user.getPassword()));
        assertEquals(registrationDate, user.getRegistrationDate());
    }
}
