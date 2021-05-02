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
public class ConsultancyMembersController {

    @Autowired
    private ControllerUtils utils;

    private final String PRIVILEG_NAME = "MEMBERS_LIST";

    @GetMapping("/memberList")
    public ModelAndView getMembersList(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/memberList",
                consultancyName,
                "Mitgliederliste",
                PRIVILEG_NAME,
                "sites/consultancy/admin/membersList",
                null
        );
        return modelAndView;
    }
}
