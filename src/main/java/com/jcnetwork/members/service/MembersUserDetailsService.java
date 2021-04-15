package com.jcnetwork.members.service;

import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembersUserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserDetails save(UserDetails userDetails) { return userDetailsRepository.save(userDetails); }

    public void delete(UserDetails userDetails) { userDetailsRepository.delete(userDetails); }

    public Optional<UserDetails> getById(String id) { return userDetailsRepository.findById(id); }
}
