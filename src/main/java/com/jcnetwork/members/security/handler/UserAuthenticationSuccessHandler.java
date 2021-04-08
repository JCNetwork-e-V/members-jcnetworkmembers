package com.jcnetwork.members.security.handler;

import com.jcnetwork.members.security.service.MembersUserDetailsService;
import com.jcnetwork.members.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();

        if(principal instanceof DefaultOidcUser) {

            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            String userMail = oidcUser.getClaim("preferred_username");

            Optional<User> user = userDetailsService.findUserByUsername(userMail);
            session.setAttribute("user", userMail);

            if(user.isEmpty()) {
                response.sendRedirect("/userRegistration");
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }

        } else {
            String userMail = authentication.getName();

            Optional<User> user = userDetailsService.findUserByUsername(userMail);
            session.setAttribute("user", userMail);

            if(user.get().getUserDetails() == null) {
                response.sendRedirect("/userRegistration");
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}
