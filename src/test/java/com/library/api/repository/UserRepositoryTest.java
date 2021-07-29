package com.library.api.repository;

import com.library.api.model.Role;
import com.library.api.model.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    void createUser() {
        User student = new User(null, "2017100000028", "poran", "chowdury",
                "chowdury@gmail.com", "password", "01757414897",
                Role.ROLE_STUDENT.name(), "CSE", "46th", true, true);
        User admin = new User(null, "hm", "Hasan", "chowdury",
                "hasan@gmail.com", "password", "01757414897",
                Role.ROLE_LIBRARIAN.name(), null, null, true, true);
        userRepository.save(student);
        userRepository.save(admin);
    }

    @Test
    @Transactional
    void getUserFinByIdTest() {
       assertEquals(userRepository.findById(1L).get().getBorrowBooks().size(),1);
    }

    @Test
    void deleteUserById() {
        userRepository.deleteById(1L);
    }
}