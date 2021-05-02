package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.Role;
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
import java.util.HashMap;
import java.util.Map;
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

        Consultancy consultancy = consultancyService.getByName(consultancyName).get();

        Map<String, Object> addedObjects = new HashMap<>();
        addedObjects.put("roles", consultancy.getRoles());

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/roleList",
                consultancyName,
                "Rollenübersicht",
                PRIVILEG_NAME,
                "sites/consultancy/admin/roles/roleList",
                addedObjects
        );
        return modelAndView;
    }

    @GetMapping("/addRole")
    public ModelAndView getAddRole(@PathVariable("consultancy") String consultancyName) {

        Optional<Consultancy> consultancy = consultancyService.getByName(consultancyName);

        Map<String, Object> addedObjects = new HashMap<>();
        addedObjects.put("role", new Role());
        addedObjects.put("organizationalEntities", consultancy.get().getOrganizationalEntities());

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/addRole",
                consultancyName,
                "Rolle hinzufügen",
                PRIVILEG_NAME,
                "sites/consultancy/admin/roles/addRole",
                addedObjects
        );
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

    @GetMapping("/roleAllocation")
    public ModelAndView getRoleAllocation(@PathVariable("consultancy") String consultancyName) {

        ModelAndView modelAndView = utils.createMainLayoutConsultancy(
                "/roleAllocation",
                consultancyName,
                "Rollen zuweisen",
                PRIVILEG_NAME,
                "sites/consultancy/admin/roles/roleAllocation",
                null
        );
        return modelAndView;
    }
}
