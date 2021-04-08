package com.jcnetwork.members.controller;

import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import com.jcnetwork.members.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Controller
public class userController {

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @RequestMapping("/userRegistration")
    public ModelAndView getUserRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", new UserDetails());
        modelAndView.setViewName("sites/users/userRegistration");
        return modelAndView;
    }

    @PostMapping("/userRegistration")
    public RedirectView createNewConsultancy(@Valid UserDetails userDetails) {

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            username = oidcUser.getClaim("preferred_username");
            user.setAzureAccounts(new HashSet<>(Arrays.asList(username)));
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> usr = userDetailsService.findUserByUsername(username);
            user = usr.get();
        }

        user.setUserDetails(userDetails);
        userDetailsService.saveUser(user);

        return new RedirectView("/home");
    }
}
