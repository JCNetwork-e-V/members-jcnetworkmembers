package com.jcnetwork.members.controller.consultancy;

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
public class ConsultancyMessageController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private ControllerUtils utils;

    @GetMapping("/messages")
    public ModelAndView getMessages(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/messages", consultancyName);
        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Nachrichten");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancy.get().getConsultancyDetails().getName());
        modelAndView.setViewName("sites/consultancy/admin/messages");
        return modelAndView;
    }

    @GetMapping("/newMessage")
    public ModelAndView newMessage(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/admin/messages", consultancyName);
        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Nachrichten");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancy.get().getConsultancyDetails().getName());
        modelAndView.setViewName("sites/consultancy/admin/messages");
        return modelAndView;
    }
}
