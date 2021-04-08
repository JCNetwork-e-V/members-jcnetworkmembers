package com.jcnetwork.members.security.service;

import com.jcnetwork.members.repository.VerificationTokenRepository;
import com.jcnetwork.members.security.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken save(VerificationToken token) { return verificationTokenRepository.save(token); }

    public Optional<VerificationToken> getToken(String token) { return verificationTokenRepository.findByToken(token); }
}
