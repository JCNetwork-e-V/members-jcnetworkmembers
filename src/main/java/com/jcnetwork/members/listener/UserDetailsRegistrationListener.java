package com.jcnetwork.members.listener;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.Member;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.event.OnUserDetailsRegistrationEvent;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.MembersUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Component
public class UserDetailsRegistrationListener implements ApplicationListener<OnUserDetailsRegistrationEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Autowired
    private ConsultancyService consultancyService;

    @Override
    public void onApplicationEvent(OnUserDetailsRegistrationEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnUserDetailsRegistrationEvent event) {

        Object principal = event.getPrincipal();
        UserDetails userDetails = event.getUserDetails();

        String username = "";
        User user = new User();

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            username = oidcUser.getClaim("preferred_username");
            user.setAzureAccounts(new HashSet<>(Arrays.asList(username)));
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            user = userService.findUserByUsername(username).get();
        }

        userDetails = userDetailsService.save(userDetails);
        user.setUserDetails(userDetails);
        userService.saveUser(user);

        Member member = new Member();
        member.setEmail(username);
        member.setUserDetails(userDetails);

        String domain = username.substring(username.indexOf("@") + 1);
        Optional<Consultancy> consultancy = consultancyService.getByDomain(domain);
        if(consultancy.isPresent()) {
            consultancy.get().getMembers().add(member);
            consultancyService.save(consultancy.get());
        }
    }
}