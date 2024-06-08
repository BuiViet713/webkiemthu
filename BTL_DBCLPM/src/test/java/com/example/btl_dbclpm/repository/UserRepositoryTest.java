package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("viet123");
        user.setPassword("Viet@12345");
        user.setEmail("QuocViet@gmail.com");
        user.setPhoneNumber("0999999999");
        userRepository.save(user);
    }

    @Test
    void testFindByUsernameAndPassword() {
        User user = userRepository.findByUsernameAndPassword("viet123", "Viet@12345");
        assertNotNull(user);
        assertEquals("Viet123", user.getUsername());
        assertEquals("Viet@12345", user.getPassword());
    }

    @Test
    void testExistsByUsername() {
        assertTrue(userRepository.existsByUsername("viet123"));
        assertFalse(userRepository.existsByUsername("nonexistentuser"));
    }

    @Test
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("quocviet@gmail.com"));
        assertFalse(userRepository.existsByEmail("quocviet1@gmail.com"));
    }

    @Test
    void testExistsByPhoneNumber() {
        assertTrue(userRepository.existsByPhoneNumber("09999999999"));
        assertFalse(userRepository.existsByPhoneNumber("0987654321"));
    }
}