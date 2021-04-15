package com.jcnetwork.members.utils;

import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ControllerUtils {

    @Autowired
    private UserService userDetailsService;

    public UserDetails getUserDetailsFromContext() {

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
                .addNavItem("Accountverwaltung", "/" + consultancyName + "/admin/accountSettings", "fa-wrench").closeNavGroup();


        if(!activePath.isEmpty()) sidebar.setActiveLinks("/" + consultancyName + "/admin" + activePath);
        return sidebar;
    }
}
