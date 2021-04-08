package com.jcnetwork.members.controller;

import com.jcnetwork.members.dto.PasswordResetDto;
import com.jcnetwork.members.dto.RegistrationDto;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Autowired
    private ControllerUtils utils;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sites/login/login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();

        RegistrationDto registration = new RegistrationDto();
        List<String> consultancies = consultancyService.getAllConsultancyNames();

        modelAndView.addObject("registration", registration);
        modelAndView.addObject("consultancies", consultancies);
        modelAndView.setViewName("sites/login/signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView createNewUser(@Valid RegistrationDto registration, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Consultancy> consultancy = consultancyService.getByName(registration.getSelectedConsultancy());
        Account account = registration.getAccount();

        Optional<User> existingUser = userDetailsService.findUserByUsername(account.getUsername());
        if (existingUser.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("sites/login/signup");
        } else {
            userDetailsService.createNewUser(account, consultancy.get(), "USER");
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("account", new Account());
            modelAndView.setViewName("sites/login/login");

        }
        return modelAndView;
    }

    @GetMapping("/passwordForgotten")
    public ModelAndView passwordForgotten() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("sites/login/passwordForgotten");
        modelAndView.addObject("passwordReset", new PasswordResetDto());
        return modelAndView;
    }

    @PostMapping("/passwordForgotten")
    public ModelAndView sendPasswordReset(@Valid PasswordResetDto passwordReset, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> existingUser = userDetailsService.findUserByUsername(passwordReset.getEmail());
        if (existingUser.isEmpty()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Es gibt keinen Nutzer mit dieser E-Mail Adresse.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("sites/login/passwordForgotten");
        } else {
            // TODO password reset
        }
        return modelAndView;
    }

    @GetMapping("/logoutSuccess")
    public ModelAndView logoutSuccess(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String userMail = (String) session.getAttribute("user");

        Optional<User> user = userDetailsService.findUserByUsername(userMail);

        if(user.isEmpty()){
            return new ModelAndView(new RedirectView("/login"));
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", user.get().getUserDetails());

        modelAndView.setViewName("sites/login/lockscreen");
        return modelAndView;
    }
}
