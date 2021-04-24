package com.jcnetwork.members.controller.consultadmin;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyDashboardController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("/dashboard")
    public ModelAndView consultancyDashboard(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/dashboard", consultancyName);
        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Admin Dashboard");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancy.get().getConsultancyDetails().getName());
        modelAndView.setViewName("sites/consultancy/admin/dashboard");
        return modelAndView;
    }
}
