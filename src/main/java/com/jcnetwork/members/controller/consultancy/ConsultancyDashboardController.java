package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyDashboardController {

    @Autowired
    private ControllerUtils utils;

    private final String PRIVILEG_NAME = "DASHBOARD";

    @GetMapping("/dashboard")
    public ModelAndView consultancyDashboard(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/dashboard",
                consultancyName,
                "Dashboard",
                PRIVILEG_NAME,
                "sites/consultancy/admin/dashboard",
                null
        );
        return modelAndView;
    }
}
