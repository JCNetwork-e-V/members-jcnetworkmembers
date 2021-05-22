package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyMembersController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private UserService userService;

    private final String PRIVILEG_NAME = "MEMBER_LIST";

    @GetMapping("/memberList")
    public ModelAndView getMembersList(@PathVariable("consultancy") String consultancyName) {

        Consultancy consultancy = consultancyService.getByName(consultancyName).get();

        try {
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "/memberList",
                    consultancyName,
                    "Mitgliederliste",
                    PRIVILEG_NAME
            );
            modelAndView.addObject("members", consultancy.getMembers());
            modelAndView.setViewName("sites/consultancy/admin/membersList");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }

    @GetMapping("/member/{id}")
    public ModelAndView getMemberProfile(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("id") String userId
    ) {

        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) return new ModelAndView(new RedirectView("/404"));
        UserDetails userDetails = optionalUser.get().getUserDetails();

        try {
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "",
                    consultancyName,
                    userDetails.getFirstName() + " " + userDetails.getLastName() + " Profil",
                    PRIVILEG_NAME
            );
            modelAndView.addObject("userDetails", userDetails);
            modelAndView.setViewName("sites/consultancy/admin/memberProfile");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }
}
