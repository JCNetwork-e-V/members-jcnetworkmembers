package com.jcnetwork.members.security.service;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.repository.AccountRoleRepository;
import com.jcnetwork.members.repository.UserRepository;
import com.jcnetwork.members.security.AccountDetails;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MembersUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByUsername(String username) {
         Optional<User> user = userRepository.findByAccountUsername(username);
         if (user.isEmpty()) {
             user = userRepository.findByAzureAccountsContaining(username);
         }
         return user;
    }

    public User createNewUser(Account account, Consultancy consultancy, String roleName) {

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setIsAccountEnabled(true); // TODO token verification
        account.setIsAccountNonLocked(true);

        User user = new User();
        user.setAccount(account);
        user.getConsultancies().add(consultancy);

        user.getRoles().add(roleRepository.findByRoleName(roleName));

        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByAccountUsername(username);
        if(user.isPresent()) {
            return new AccountDetails(user.get());
        } else {
            throw new UsernameNotFoundException("user " + username + " not found");
        }
    }
}