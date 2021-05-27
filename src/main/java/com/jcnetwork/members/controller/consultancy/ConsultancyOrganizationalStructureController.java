package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyOrganizationalStructureController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    private final String PRIVILEG_NAME = "ORGANIZATIONAL_STRUCTURE";

    @GetMapping("/consultancyStructure")
    public ModelAndView getOrganizationalStructure(@PathVariable("consultancy") String consultancyName) {

        try {
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "/consultancyStructure",
                    consultancyName,
                    "Vereinsstruktur",
                    PRIVILEG_NAME
            );
            modelAndView.setViewName("sites/consultancy/admin/organizationalStructure/organizationalChart");
            return modelAndView;
        } catch (Exception e){
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }

    @GetMapping("/memberAllocation")
    public ModelAndView getRoleAllocation(@PathVariable("consultancy") String consultancyName) {

        try {
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "/membersAllocation",
                    consultancyName,
                    "Mitglider zuweisen",
                    PRIVILEG_NAME
            );
            modelAndView.setViewName("sites/consultancy/admin/organizationalStructure/membersAllocation");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }
}
