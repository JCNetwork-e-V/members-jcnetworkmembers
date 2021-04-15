package com.jcnetwork.members.security.handler;

import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userDetailsService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        if (!isAccountEnabled(authentication)) request.logout();
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Boolean isAccountEnabled(Authentication authentication){

        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOidcUser) {
            return true;
        } else {
            Optional<User> user = userDetailsService.findUserByUsername(authentication.getName());
            return user.get().getAccount().getIsAccountEnabled();
        }
    }
}
