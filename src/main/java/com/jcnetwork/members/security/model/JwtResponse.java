package com.jcnetwork.members.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class JwtResponse {

    @NonNull
    private String token;
}
