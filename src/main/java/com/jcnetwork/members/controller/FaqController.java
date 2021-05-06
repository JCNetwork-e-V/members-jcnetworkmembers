package com.jcnetwork.members.controller;

import com.jcnetwork.members.model.data.user.resume.Resume;
import com.jcnetwork.members.model.data.user.resume.UniversityEducation;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FaqController {

    @Autowired
    private ControllerUtils utils;

    @GetMapping("/faq")
    public ModelAndView getFaq() {

        User user = utils.getUserFromContext();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", user.getUserDetails());
        modelAndView.addObject("userSettings", user.getUserSettings());
        modelAndView.addObject("navbarLinks", utils.navbarLinks(user));
        modelAndView.setViewName("sites/faq");
        return modelAndView;
    }
}
