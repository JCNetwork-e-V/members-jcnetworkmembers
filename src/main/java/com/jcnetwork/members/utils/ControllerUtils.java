package com.jcnetwork.members.utils;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
public class ControllerUtils {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ConsultancyService consultancyService;

    public ModelAndView createMainLayoutAdmin(String activePath, String contentHeader) {

        UserDetails userDetails = getUserDetailsFromContext();
        Sidebar sidebar = adminSidebar(activePath);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", contentHeader);
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", "Admin");
        return modelAndView;
    }

    public ModelAndView createMainLayoutConsultancy(String activePath, String consultancyName, String contentHeader) {

        UserDetails userDetails = getUserDetailsFromContext();
        Sidebar sidebar = consultancySidebar(activePath, consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", contentHeader);
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancyName);
        return modelAndView;
    }

    private UserDetails getUserDetailsFromContext() {

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            username = oidcUser.getClaim("preferred_username");
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Optional<User> user = userDetailsService.findUserByUsername(username);

        if(user.isPresent()) return user.get().getUserDetails();
        else return null;
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

    public Sidebar consultancySidebar(String activePath, String consultancyName) {

        Sidebar sidebar = new Sidebar();
        sidebar
                .addNavGroup()
                .addNavItem("Dashboard", "/" + consultancyName + "/admin/dashboard", "fa-tachometer-alt").topLevel()
                .addNavItem("Nachrichten", "/" + consultancyName + "/admin/messages", "fa-envelope").closeNavGroup()
                .addNavGroup("Mitgliederverwaltung")
                .addNavItem("Mitgliederliste", "/" + consultancyName + "/admin/memberList", "fa-list").closeNavGroup()
                .addNavGroup("Vereinsorganisation")
                .addNavItem("Organisationsstruktur", "/" + consultancyName + "/admin/consultancyStructure", "fa-sitemap").closeNavGroup()
                .addNavGroup("Einstellungen")
                .addNavItem("Rollenverwaltung", "#", "fa-user-tag")
                .addSubItem("Rollenverzeichnis", "/" + consultancyName + "/admin/roleList", "fa-tags").and()
                .addSubItem("Rolle hinzufügen", "/" + consultancyName + "/admin/addRole", "fa-plus").and()
                .addSubItem("Rollen zuweisen", "/" + consultancyName + "/admin/roleAllocation", "fa-user-plus").topLevel()
                .addNavItem("Vereinseinstellungen", "/" + consultancyName + "/admin/accountSettings", "fa-wrench").closeNavGroup();


        if(!activePath.isEmpty()) sidebar.setActiveLinks("/" + consultancyName + "/admin" + activePath);
        return sidebar;
    }
}
