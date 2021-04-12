package com.jcnetwork.members.controller.admin;

import com.jcnetwork.members.model.dto.ConsultancyCreationDto;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private ControllerUtils utils;

    @GetMapping("/dashboard")
    public ModelAndView getDashboard() {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = adminSidebar("/admin/dashboard");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Admin Dashboard");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.setViewName("sites/admin/dashboard");
        return modelAndView;
    }

    @GetMapping("/createConsultancy")
    public ModelAndView getConsultancyCreationForm() {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = adminSidebar("/admin/createConsultancy");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Verein anlegen");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancy", new ConsultancyCreationDto());
        modelAndView.setViewName("sites/admin/createConsultancy");
        return modelAndView;
    }

    @PostMapping("/createConsultancy")
    public ModelAndView createNewConsultancy(@Valid ConsultancyCreationDto consultancyCreationDto) {

        Optional<Consultancy> consultancyExists = consultancyService.getByName(consultancyCreationDto.getName());
        if (consultancyExists.isPresent()) {
            // TODO send back to site with InternalMessage
        } else {
            Consultancy registered = consultancyService.registerNewConsultancy(consultancyCreationDto);
        }
        return getConsultancyList();
    }

    @GetMapping("/consultancyList")
    public ModelAndView getConsultancyList() {

        UserDetails userDetails = utils.getUserDetailsFromContext();

        Sidebar sidebar = adminSidebar("/admin/consultancyList");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Vereinsliste");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancies", consultancyService.getAll());
        modelAndView.setViewName("sites/admin/consultancyList");
        return modelAndView;
    }


    private Sidebar adminSidebar(String activePath) {

        Sidebar sidebar = new Sidebar();
        sidebar
            .addNavGroup()
                .addNavItem("Dashboard", "/admin/dashboard", "fa-tachometer-alt").topLevel()
                .addNavItem("Meldungen", "/admin/messages", "fa-envelope").closeNavGroup()
            .addNavGroup("Vereinsverwaltung")
                .addNavItem("Vereinsliste", "/admin/consultancyList", "fa-list").topLevel()
                .addNavItem("Verein anlegen", "/admin/createConsultancy", "fa-plus").topLevel()
                .addNavItem("Verein löschen", "/admin/deleteConsultancy", "fa-eraser").closeNavGroup()
            .addNavGroup("Nutzerverwaltung")
                .addNavItem("Nutzerliste", "/admin/userList", "fa-users");

        if(!activePath.isEmpty()) sidebar.setActiveLinks(activePath);
        return sidebar;
    }
}
