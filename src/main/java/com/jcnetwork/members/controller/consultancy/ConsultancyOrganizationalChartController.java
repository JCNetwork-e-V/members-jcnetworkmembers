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
public class ConsultancyOrganizationalChartController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    private final String PRIVILEG_NAME = "ORGANIZATIONAL_STRUCTURE";

    @GetMapping("/consultancyStructure")
    public ModelAndView getOrganizationalStructure(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/consultancyStructure",
                consultancyName,
                "Vereinsstruktur",
                PRIVILEG_NAME,
                "sites/consultancy/admin/organizationalStructure",
                null
        );
        return modelAndView;
    }
}
