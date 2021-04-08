package com.jcnetwork.members.security.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Account {

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Boolean isAccountNonLocked;
    @NonNull
    private Boolean isAccountEnabled;
}
