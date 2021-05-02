package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyMessageController {

    @Autowired
    private ControllerUtils utils;

    private final String PRIVILEG_NAME = "MESSAGES";

    @GetMapping("/messages")
    public ModelAndView getMessages(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/messages",
                consultancyName,
                "Nachrichten",
                PRIVILEG_NAME,
                "sites/consultancy/admin/messages",
                null
        );
        return modelAndView;
    }

    @GetMapping("/newMessage")
    public ModelAndView newMessage(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/messages",
                consultancyName,
                "Neue Nachricht formulieren",
                PRIVILEG_NAME,
                "sites/consultancy/admin/messages",
                null
        );
        //TODO create site
        return modelAndView;
    }
}
