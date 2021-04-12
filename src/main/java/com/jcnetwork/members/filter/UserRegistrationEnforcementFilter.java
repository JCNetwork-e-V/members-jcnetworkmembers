package com.jcnetwork.members.filter;

import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRegistrationEnforcementFilter extends GenericFilterBean {

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Boolean sendToRegistration = false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!authentication.getName().equals("anonymousUser")){
            if(isRequestRedirectable(request)){

                Object principal = authentication.getPrincipal();
                if (principal instanceof DefaultOidcUser) {

                    DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                    String username = oidcUser.getClaim("preferred_username");

                    Optional<User> user = userDetailsService.findUserByUsername(username);

                    if (user.isEmpty()) {
                        sendToRegistration = true;
                    }
                } else {
                    String userMail = authentication.getName();

                    Optional<User> user = userDetailsService.findUserByUsername(userMail);

                    if (user.get().getUserDetails() == null) {
                        sendToRegistration = true;
                    }
                }
            }
        }
        if(sendToRegistration){
            response.sendRedirect("/userRegistration");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private Boolean isRequestRedirectable(HttpServletRequest request) {

        String path = request.getRequestURI();
        if (path.equals("/userRegistration") ||
                path.startsWith("/api") ||
                path.startsWith("/custom") ||
                path.startsWith("/images") ||
                path.startsWith("/plugins")) return false;
        return true;
    }
}
