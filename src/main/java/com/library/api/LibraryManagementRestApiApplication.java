package com.library.api;

import com.library.api.model.Role;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibraryManagementRestApiApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementRestApiApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User(null, "hm", "Hasan", "chowdury",
                "hasan@gmail.com", "abc", "01757414897",
                Role.ROLE_LIBRARIAN.name(), null, null, true, true);
        String encode = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);
        if (!userRepository.findByUsername(admin.getUsername()).isPresent()){
            userRepository.save(admin);
        }

    }
}
