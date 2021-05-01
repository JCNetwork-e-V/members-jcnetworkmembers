package com.jcnetwork.members.controller.admin;

import com.jcnetwork.members.mapper.ConsultancyMapper;
import com.jcnetwork.members.model.dto.ConsultancyCreationDto;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.event.OnConsultancyCreationEvent;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private ConsultancyMapper mapper;

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/dashboard")
    public ModelAndView getDashboard() {

        ModelAndView modelAndView = utils.createMainLayoutAdmin(
                "/dashboard",
                "Dashboard"
        );
        modelAndView.setViewName("sites/admin/dashboard");
        return modelAndView;
    }

    @GetMapping("/createConsultancy")
    public ModelAndView getConsultancyCreationForm() {

        ModelAndView modelAndView = utils.createMainLayoutAdmin(
                "/admin/createConsultancy",
                "Neuen Verein anlegen"
        );
        modelAndView.setViewName("sites/admin/createConsultancy");
        return modelAndView;
    }

    @PostMapping("/createConsultancy")
    public ModelAndView createNewConsultancy(@Valid ConsultancyCreationDto consultancyCreationDto) {

        Optional<Consultancy> consultancyExists = consultancyService.getByName(consultancyCreationDto.getName());
        if (consultancyExists.isPresent()) {
            // TODO send back to site with InternalMessage
        } else {
            eventPublisher.publishEvent(new OnConsultancyCreationEvent(consultancyCreationDto));
        }
        return getConsultancyList();
    }

    @GetMapping("/consultancyList")
    public ModelAndView getConsultancyList() {

        ModelAndView modelAndView = utils.createMainLayoutAdmin(
                "/admin/consultancyList",
                "Vereinsliste"
        );
        modelAndView.addObject("consultancies", mapper.toDto(consultancyService.getAll()));
        modelAndView.setViewName("sites/admin/consultancyList");
        return modelAndView;
    }
}
