package com.springsecurtiyimpl.config;

import com.springsecurtiyimpl.entity.NetOpsMember;
import com.springsecurtiyimpl.entity.TicketCount;
import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.repo.NetOpsUserRepository;
import com.springsecurtiyimpl.repo.TicketCountRepo;
import com.springsecurtiyimpl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private NetOpsUserRepository netOpsMemberRepo;

    @Autowired
    private TicketCountRepo ticketCountRepo;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("vivek").isEmpty()) {
            User user = new User();
            user.setUsername("vivek");
            user.setPassword(passwordEncoder.encode("root"));
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }

        if (userRepository.findByUsername("adminVivek").isEmpty()) {
            User admin = new User();
            admin.setUsername("adminVivek");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        if (netOpsMemberRepo.count() == 0) {
            netOpsMemberRepo.save(NetOpsMember.builder()
                    .name("Rishikesh singh")
                    .email("rishi.singh@company.com")
                    .isActive(true)
                    .build());

            netOpsMemberRepo.save(NetOpsMember.builder()
                    .name("Jane Smith")
                    .email("jane.smith@company.com")
                    .isActive(true)
                    .build());

            netOpsMemberRepo.save(NetOpsMember.builder()
                    .name("Bob Johnson")
                    .email("bob.johnson@company.com")
                    .isActive(true)
                    .build());

        }
        LocalDate today = LocalDate.now();
        List<NetOpsMember> activeUsers = netOpsMemberRepo.findByIsActiveTrue();

        for (NetOpsMember user : activeUsers) {
            boolean exists = ticketCountRepo.findByNetOpsMemberAndDate(user, today).isPresent();
            if (!exists) {
                ticketCountRepo.save(TicketCount.builder()
                        .netOpsMember(user)
                        .date(today)
                        .count(0)
                        .build());
            }
        }

        System.out.println("Daily assignment counts initialized for " + activeUsers.size() + " users");

        }
    }



