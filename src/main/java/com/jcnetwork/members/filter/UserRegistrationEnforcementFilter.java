package com.jcnetwork.members.filter;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.Member;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserRegistrationEnforcementFilter extends GenericFilterBean {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ConsultancyService consultancyService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!authentication.getName().equals("anonymousUser")){
            if(isRequestRedirectable(request)) {
                if (checkForInitialRegistration(authentication)) {
                    response.sendRedirect("/userRegistration");
                } else if(
                        !request.getRequestURI().endsWith("/accountSettings") &&
                        !request.getRequestURI().endsWith("/addCustomField") &&
                        !request.getRequestURI().endsWith("/deleteCustomField")
                ) {
                    String incompleteConsultancy = checkForNewDataField(authentication);
                    if(!incompleteConsultancy.isEmpty()){
                        response.sendRedirect("/" + incompleteConsultancy + "/dataCompletion?url=" + request.getRequestURI());
                    } else {
                        filterChain.doFilter(request, response);
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkForInitialRegistration(Authentication authentication) {

        Boolean redirectUser = false;

        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOidcUser) {

            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            String username = oidcUser.getClaim("preferred_username");

            Optional<User> user = userDetailsService.findUserByUsername(username);

            if (user.isEmpty()) {
                redirectUser = true;
            }
        } else {
            String userMail = authentication.getName();

            Optional<User> user = userDetailsService.findUserByUsername(userMail);

            if (user.get().getUserDetails() == null) {
                redirectUser = true;
            }
        }
        return redirectUser;
    }

    private String checkForNewDataField(Authentication authentication) {

        String incompleteConsultancy = "";

        Object principal = authentication.getPrincipal();
        User user = null;

        if (principal instanceof DefaultOidcUser) {

            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            String username = oidcUser.getClaim("preferred_username");

            user = userDetailsService.findUserByUsername(username).get();

        } else {
            String userMail = authentication.getName();

            user = userDetailsService.findUserByUsername(userMail).get();
        }

        Optional<Consultancy> consultancy = null;
        Member member = null;
        Set<String> userMails = new HashSet<>();
        if (user.getAccount() != null) userMails.add(user.getAccount().getUsername());
        if (user.getAzureAccounts() != null) userMails.addAll(user.getAzureAccounts());

        for (String userMail : userMails) {
            consultancy = consultancyService.getByDomain(userMail.substring(userMail.indexOf("@") + 1));
            if (consultancy.isPresent()){
                member = consultancy.get().getMemberByEmail(userMail);
                if (member.getHasNewDataField()) incompleteConsultancy = consultancy.get().getConsultancyDetails().getName();
            }
        }
        return incompleteConsultancy;
    }

    private Boolean isRequestRedirectable(HttpServletRequest request) {

        String path = request.getRequestURI();
        if (path.equals("/userRegistration") ||
                path.endsWith("/dataCompletion") ||
                path.startsWith("/api") ||
                path.startsWith("/custom") ||
                path.startsWith("/images") ||
                path.startsWith("/plugins")) return false;
        return true;
    }
}
