package com.jcnetwork.members.controller.consultadmin;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.Role;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.model.ui.sidemenu.Sidebar;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyRoleController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("/roleList")
    public ModelAndView roleList(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/roleList", consultancyName);
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", consultancy.getRoles());
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Rollenübersicht");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancy.getConsultancyDetails().getName());
        modelAndView.setViewName("sites/consultancy/admin/roles/roleList");
        return modelAndView;
    }

    @GetMapping("/addRole")
    public ModelAndView getAddRole(@PathVariable("consultancy") String consultancyName) {

        UserDetails userDetails = utils.getUserDetailsFromContext();
        Sidebar sidebar = utils.consultancySidebar("/addRole", consultancyName);
        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role", new Role());
        modelAndView.addObject("organizationalEntities", consultancy.get().getOrganizationalEntities());
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("contentHeader", "Rolle Hinzufügen");
        modelAndView.addObject("sidebar", sidebar);
        modelAndView.addObject("consultancyName", consultancy.get().getConsultancyDetails().getName());
        modelAndView.setViewName("sites/consultancy/admin/roles/addRole");
        return modelAndView;
    }

    @PostMapping("/addRole")
    public RedirectView createNewRole(
            @Valid Role role,
            @PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = new ModelAndView();
        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow( () -> new ItemNotFoundException("Verein existiert nicht")); //TODO real error page

        Set<Role> roles = consultancy.getRoles();
        for(Role existingRole : roles) {
            if(existingRole.getName() == role.getName()){
                return new RedirectView("addRole");
            }
        }

        roles.add(role);
        consultancy.setRoles(roles);
        consultancyService.save(consultancy);
        return new RedirectView("roleList");
    }
}
