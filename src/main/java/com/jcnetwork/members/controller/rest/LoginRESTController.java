package com.jcnetwork.members.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcnetwork.members.security.AccountDetails;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import com.jcnetwork.members.security.service.TokenService;
import com.jcnetwork.members.security.model.JwtRequest;
import com.jcnetwork.members.security.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginRESTController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/generateToken")
    public ResponseEntity generateToken(@RequestBody JwtRequest authenticationRequest) throws JsonProcessingException {

        AccountDetails accountDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword(),
                        accountDetails.getAuthorities()));

        final Optional<User> apiUser = userDetailsService.findUserByUsername(
                authenticationRequest.getUsername());
        final String token = tokenService.generateToken(apiUser.get());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
