package com.jcnetwork.members.security.service;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.repository.AccountRoleRepository;
import com.jcnetwork.members.repository.UserRepository;
import com.jcnetwork.members.repository.VerificationTokenRepository;
import com.jcnetwork.members.security.AccountDetails;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.model.VerificationToken;
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
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByUsername(String username) {
         Optional<User> user = userRepository.findByAccountUsername(username);
         if (user.isEmpty()) {
             user = userRepository.findByAzureAccountsContaining(username);
         }
         return user;
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public User createNewUser(Account account, Consultancy consultancy, String roleName) {

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setIsAccountEnabled(false);
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

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        tokenRepository.save(myToken);
    }

    public Optional<VerificationToken> getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
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