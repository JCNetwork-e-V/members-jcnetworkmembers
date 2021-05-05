package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.Role;
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

    private final String PRIVILEG_NAME = "ROLE_MANAGEMENT";

    @GetMapping("/roleList")
    public ModelAndView roleList(@PathVariable("consultancy") String consultancyName) {

        try {
            Consultancy consultancy = consultancyService.getByName(consultancyName).get();

            ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                    "/roleList",
                    consultancyName,
                    "Rollenübersicht",
                    PRIVILEG_NAME
            );
            modelAndView.addObject("roles", consultancy.getRoles());
            modelAndView.setViewName("sites/consultancy/admin/roles/roleList");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }

    @GetMapping("/addRole")
    public ModelAndView getAddRole(@PathVariable("consultancy") String consultancyName) {

        try {
            Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

            ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                    "/addRole",
                    consultancyName,
                    "Rolle hinzufügen",
                    PRIVILEG_NAME
            );
            modelAndView.addObject("role", new Role());
            modelAndView.addObject("organizationalEntities", consultancy.get().getOrganizationalEntities());
            modelAndView.setViewName("sites/consultancy/admin/roles/addRole");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
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

    @GetMapping("/roleAllocation")
    public ModelAndView getRoleAllocation(@PathVariable("consultancy") String consultancyName) {

        try {
            ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                    "/roleAllocation",
                    consultancyName,
                    "Rollen zuweisen",
                    PRIVILEG_NAME
            );
            modelAndView.setViewName("sites/consultancy/admin/roles/roleAllocation");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }
}
