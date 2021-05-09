package com.jcnetwork.members.controller;

import com.jcnetwork.members.mapper.ConsultancyMapper;
import com.jcnetwork.members.model.dto.RegistrationDto;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.event.OnUserDetailsRegistrationEvent;
import com.jcnetwork.members.model.event.OnUserRegistrationCompleteEvent;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.VerificationToken;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private UserService userService;

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/signup")
    public ModelAndView signup() {
        // TODO redirect if already filled out
        RegistrationDto registration = new RegistrationDto();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("registration", registration);
        modelAndView.setViewName("sites/login/signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView createNewUser(@Valid RegistrationDto registration, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<Consultancy> consultancy = consultancyService.getByName(registration.getSelectedConsultancy());
        Account account = registration.getAccount();

        Optional<User> existingUser = userService.findUserByUsername(account.getUsername());
        if (existingUser.isPresent()) {
            // TODO error InternalMessage
            modelAndView.setView(new RedirectView("/signup"));
        } else {
            User registered = userService.createNewUser(account, consultancy.get(), "USER");
            eventPublisher.publishEvent(new OnUserRegistrationCompleteEvent(registered));
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("account", new Account());
            modelAndView.setViewName("sites/login/login");

        }
        return modelAndView;
    }

    @GetMapping("/userRegistration")
    public ModelAndView getUserDetailsRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", new UserDetails());
        modelAndView.setViewName("sites/users/userRegistration");
        return modelAndView;
    }

    @PostMapping("/userRegistration")
    public RedirectView userDetailsRegistration(@Valid UserDetails userDetails) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        eventPublisher.publishEvent(new OnUserDetailsRegistrationEvent(principal, userDetails));

        return new RedirectView("/home");
    }

    @GetMapping("/registrationConfirmation")
    public RedirectView confirmUserRegistration(@RequestParam("token") String token) {

        Optional<VerificationToken> verificationToken = userService.getVerificationToken(token);

        if (verificationToken.isEmpty()) {
            // TODO send user to error page
            return new RedirectView("/error");
        }
        if (verificationToken.get().getExpiryDate().isBefore(LocalDate.now())) {
            // TODO send token expiration error
            return new RedirectView("/error");
        }
        User user = verificationToken.get().getUser();
        user.getAccount().setIsAccountEnabled(true);
        userService.saveUser(user);

        return new RedirectView("/login");
    }

    @GetMapping("/consultancyRegistration")
    public ModelAndView getConsultancyRegistrationForm() {
        // TODO redirect if already filled out

        //String consultancyName = utils.getUserDetailsFromContext().getFirstName();

        //Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        //modelAndView.addObject("consultancyDetails", consultancy.get().getConsultancyDetails());
        modelAndView.setViewName("sites/consultancy/admin/consultancyRegistration");
        return modelAndView;
    }
}
