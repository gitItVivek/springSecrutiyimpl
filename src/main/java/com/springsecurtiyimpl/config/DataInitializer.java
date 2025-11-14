package com.springsecurtiyimpl.config;

import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("vivek").isEmpty()){
            User user = new User();
            user.setUsername("vivek");
            user.setPassword(passwordEncoder.encode("root"));
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }

        if(userRepository.findByUsername("adminVivek").isEmpty()){
            User admin = new User();
            admin.setUsername("adminVivek");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

    }
}
