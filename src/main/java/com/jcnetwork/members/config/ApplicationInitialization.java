package com.jcnetwork.members.config;

import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.repository.AccountRoleRepository;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.AccountRole;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationInitialization {

    @Autowired
    private AccountRoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public CommandLineRunner init() {

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

            Optional<User> systemUser = userService.findUserByUsername("System_Admin");
            if(systemUser.isEmpty()){
                Account systemAccount = new Account();
                systemAccount.setUsername("System_Admin");
                systemAccount.setPassword(bCryptPasswordEncoder.encode("1234")); //TODO real password
                systemAccount.setIsAccountEnabled(true);
                systemAccount.setIsAccountNonLocked(true);

                UserDetails systemUserDetails = new UserDetails();
                systemUserDetails.setFirstName("JCNetwork");
                systemUserDetails.setLastName("Members");

                User user = new User();
                user.setAccount(systemAccount);
                user.setUserDetails(systemUserDetails);
                user.getRoles().add(roleRepository.findByRoleName("ADMIN"));
                userService.saveUser(user);
            }
        };
    }
}
