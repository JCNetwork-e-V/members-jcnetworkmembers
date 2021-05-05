package com.jcnetwork.members.controller.consultancy;

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
public class ConsultancyAccountSettingsController {

    @Autowired
    private ControllerUtils utils;

    private final String PRIVILEGE_NAME = "ACCOUNT_SETTINGS";

    @GetMapping("/accountSettings")
    public ModelAndView getAccountSettings(@PathVariable("consultancy") String consultancyName) {

        try {
            ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                    "accountSettings",
                    consultancyName,
                    "Vereinseinstellungen",
                    PRIVILEGE_NAME
            );
            modelAndView.setViewName("sites/consultancy/admin/accountSettings");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }
}
