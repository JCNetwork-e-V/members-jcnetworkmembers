package com.jcnetwork.members.config;

import com.jcnetwork.members.repository.AccountRoleRepository;
import com.jcnetwork.members.security.model.AccountRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitialization {

    @Bean
    CommandLineRunner init(AccountRoleRepository roleRepository) {

        return args -> {

            AccountRole adminRole = roleRepository.findByRoleName("ADMIN");
            if (adminRole == null) {
                AccountRole newAdminRole = new AccountRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            AccountRole consultancyAdminRole = roleRepository.findByRoleName("CONSULTANCY_ADMIN");
            if (consultancyAdminRole == null) {
                AccountRole newConsultancyAdminRole = new AccountRole("CONSULTANCY_ADMIN");
                roleRepository.save(newConsultancyAdminRole);
            }

            AccountRole userRole = roleRepository.findByRoleName("USER");
            if (userRole == null) {
                AccountRole newUserRole = new AccountRole("USER");
                roleRepository.save(newUserRole);
            }
        };

    }
}
