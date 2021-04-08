package com.jcnetwork.members.controller;

import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.service.MailService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Component
@RestController
public class TestController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private MailService mailService;

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView getUserRegistrationForm() {

        UserDetails userDetails = utils.getUserDetailsFromContext();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.setViewName("mainLayout");
        return modelAndView;
    }
}
