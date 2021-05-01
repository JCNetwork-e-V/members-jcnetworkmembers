package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.mapper.MemberMapper;
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
public class ConsultancyMembersController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private MemberMapper mapper;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("/memberList")
    public ModelAndView getMembersList(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/memberList", consultancyName);
        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Mitgliederliste");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("members", mapper.toDto(consultancy.get().getMembers()));
        modelAndView.setViewName("sites/consultancy/admin/membersList");
        return modelAndView;
    }
}
