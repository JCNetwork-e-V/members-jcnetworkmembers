package com.jcnetwork.members.utils;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.Member;
import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.ui.NavBarItem;
import com.jcnetwork.members.model.ui.sidemenu.NavGroup;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Component
public class ControllerUtils {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ConsultancyService consultancyService;

    public ModelAndView createLayoutAdmin(String activePath, String contentHeader) {

        User user =  getUserFromContext();
        UserDetails userDetails = user.getUserDetails();
        Sidebar sidebar = adminSidebar(activePath);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", contentHeader);
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("userSettings", user.getUserSettings());
        modelAndView.addObject("consultancyName", "Admin");
        return modelAndView;
    }

    public ModelAndView createLayoutConsultancy(
            String activePath,
            String consultancyName,
            String contentHeader,
            String accessedPrivilege
    ) throws Exception {

        User user =  getUserFromContext();
        UserDetails userDetails = user.getUserDetails();
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        Set<String> privileges = getUserPrivileges(user, consultancy);
        Sidebar sidebar = consultancySidebar(activePath, consultancyName, privileges);

        ModelAndView modelAndView = new ModelAndView();

        if(!privileges.contains(accessedPrivilege)) {
            throw new Exception("FORBIDDEN");
        }

        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", contentHeader);
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("navbarLinks", navbarLinks(user));
        modelAndView.addObject("userSettings", user.getUserSettings());
        modelAndView.addObject("consultancyName", consultancyName);
        modelAndView.addObject("primaryColor", consultancy.getConsultancyDetails().getPrimaryColor());
        modelAndView.addObject("secondaryColor", consultancy.getConsultancyDetails().getSecondaryColor());
        return modelAndView;
    }

    public List<NavBarItem> navbarLinks(User user){

        Set<String> userMails = user.getAzureAccounts();
        if(user.getAccount() != null) userMails.add(user.getAccount().getUsername());

        List<NavBarItem> links = new ArrayList<>();
        links.add(new NavBarItem("Home", "/home"));
        if(user.getRoles().contains("ADMIN")){
            links.add(new NavBarItem("Admin", "/admin/dashboard"));
        }
        for(String mail : userMails) {
            String consultancyName = consultancyService.getByDomain(mail.substring(mail.indexOf("@") + 1)).get().getConsultancyDetails().getName();
            links.add(new NavBarItem(consultancyName, "/" + consultancyName + "/admin/dashboard")); //TODO create consultancy home page
        }
        links.add(new NavBarItem("FAQ", "/faq"));
        return links;
    }

    public User getUserFromContext() {

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            username = oidcUser.getClaim("preferred_username");
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Optional<User> user = userDetailsService.findUserByUsername(username);

        if(user.isPresent()) return user.get();
        else return null;
    }

    private Set<String> getUserPrivileges(User user, Consultancy consultancy) {

        Set<String> privileges = new HashSet<>();
        Set<String> userMails = user.getAzureAccounts();
        if(user.getAccount() != null) userMails.add(user.getAccount().getUsername());

        for(String mail : userMails) {
            if (mail.substring(mail.indexOf("@") + 1).equals(consultancy.getConsultancyDetails().getDomain())) {
                Member member = consultancy.getMemberByEmail(mail);

                try {
                    for (String roleName : member.getRoles()) {
                        privileges.addAll(consultancy.getRole(roleName).getPrivileges());
                    }
                } catch (Exception e) {

                }
            }
        }
        return privileges;
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

    public Sidebar consultancySidebar(String activePath, String consultancyName, Set<String> privileges) {

        Sidebar sidebar = new Sidebar();

        if(privileges.contains("DASHBOARD") || privileges.contains("MESSAGES")){
            NavGroup ng = sidebar.addNavGroup();
            if(privileges.contains("DASHBOARD"))
                ng.addNavItem("Dashboard", "/" + consultancyName + "/admin/dashboard", "fa-tachometer-alt");
            if(privileges.contains("MESSAGES"))
                ng.addNavItem("Nachrichten", "/" + consultancyName + "/admin/messages", "fa-envelope");
        }
        if(privileges.contains("MEMBER_LIST"))
            sidebar
                .addNavGroup("Mitgliederverwaltung")
                    .addNavItem("Mitgliederliste", "/" + consultancyName + "/admin/memberList", "fa-list");
        if(privileges.contains("ORGANIZATIONAL_STRUCTURE"))
            sidebar
                .addNavGroup("Vereinsorganisation")
                    .addNavItem("Organisationsstruktur", "/" + consultancyName + "/admin/consultancyStructure", "fa-sitemap").topLevel()
                    .addNavItem("Mitglieder Zuteilen", "/" + consultancyName + "/admin/memberAllocation", "fa-user-check");
        if(privileges.contains("ROLE_MANAGEMENT") || privileges.contains("ACCOUNT_SETTINGS")){
            NavGroup ng = sidebar.addNavGroup("Einstellungen");
            if(privileges.contains("ROLE_MANAGEMENT"))
                ng.addNavItem("Rollenverwaltung", "#", "fa-user-tag")
                    .addSubItem("Rollenverzeichnis", "/" + consultancyName + "/admin/roleList", "fa-tags").and()
                    .addSubItem("Rolle hinzufügen", "/" + consultancyName + "/admin/addRole", "fa-plus").and()
                    .addSubItem("Rollen zuweisen", "/" + consultancyName + "/admin/roleAllocation", "fa-user-plus");
            if(privileges.contains("ACCOUNT_SETTINGS"))
                ng.addNavItem("Vereinseinstellungen", "/" + consultancyName + "/admin/accountSettings", "fa-wrench");
        }

        if(!activePath.isEmpty()) sidebar.setActiveLinks("/" + consultancyName + "/admin" + activePath);
        return sidebar;
    }
}
