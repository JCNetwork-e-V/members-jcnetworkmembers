package com.jcnetwork.members.controller;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.security.model.VerificationToken;
import com.jcnetwork.members.security.service.VerificationTokenService;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ConsultancyAdministrationController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private VerificationTokenService tokenService;

    @GetMapping("/registrationConfirmation")
    public RedirectView confirmConsultancyRegistration(@RequestParam("token") String token) {

        Optional<VerificationToken> verificationToken = tokenService.getToken(token);

        if (verificationToken.isEmpty()) {
            // TODO send user to error page
            return new RedirectView("/error");
        }
        if (verificationToken.get().getExpiryDate().isBefore(LocalDate.now())) {
            // TODO send token expiration error
            return new RedirectView("/error");
        }
        Optional<Consultancy> consultancy = consultancyService.getById(verificationToken.get().getRelatedObjectId());
        consultancy.get().setEnabled(true);
        consultancyService.save(consultancy.get());

        return new RedirectView("/" + consultancy.get().getName() + "/home");
    }

    @GetMapping("/{consultancy}/home")
    public ModelAndView consultancyHome(@PathVariable String consultancyName) {

        ModelAndView modelAndView = new ModelAndView("sites/consultancy/home");
        return modelAndView;
    }

    private Sidebar consultancySidebar(String activePath, String consultancyName) {

        Sidebar sidebar = new Sidebar();
        sidebar
            .addNavGroup()
                .addNavItem("Dashboard", "/" + consultancyName + "/dashboard", "fa-tachometer-alt").topLevel()
                .addNavItem("Meldungen", "/" + consultancyName + "/messages", "fa-envelope").closeNavGroup()
            .addNavGroup("Mitgliederverwaltung")
                .addNavItem("Mitgliederliste", "/" + consultancyName + "/consultancyList", "fa-list").closeNavGroup()
            .addNavGroup("Ressortverwaltung")
                .addNavItem("Ressortliste", "/" + consultancyName + "/departmentList", "fa-list-ul").topLevel()
                .addNavItem("Ressorts verwalten", "/" + consultancyName + "/departmentAdministration", "fa-sitemap").closeNavGroup()
            .addNavGroup("Einstellungen")
                .addNavItem("Rollenverwaltung", "#", "fa-user-tag")
                    .addSubItem("Rollenverzeichnis", "/" + consultancyName + "/roleList", "fa-tags")
                    .addSubItem("Rolle hinzufügen", "/" + consultancyName + "/addRole", "fa-plus").and()
                    .addSubItem("Rollen zuweisen", "/" + consultancyName + "/roleAllocation", "fa-user-plus").topLevel()
                .addNavItem("Accountverwaltung", "/" + consultancyName + "/accountSettings", "fa-wrench").closeNavGroup();


        if(!activePath.isEmpty()) sidebar.setActiveLinks(activePath);
        return sidebar;
    }
}
