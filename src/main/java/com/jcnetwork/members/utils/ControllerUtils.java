package com.jcnetwork.members.utils;

import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import com.jcnetwork.members.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ControllerUtils {

    @Autowired
    private MembersUserDetailsService userDetailsService;

    public UserDetails getUserDetailsFromContext() {

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            username = oidcUser.getClaim("preferred_username");
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Optional<User> user = userDetailsService.findUserByUsername(username);

        if(user.isPresent()) return user.get().getUserDetails();
        else return null;
    }
}
