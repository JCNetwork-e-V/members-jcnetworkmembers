package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.ConsultancyDetails;
import com.jcnetwork.members.model.data.consultancy.CustomDataField;
import com.jcnetwork.members.model.ui.Toast;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyAccountSettingsController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    private final String PRIVILEGE_NAME = "ACCOUNT_SETTINGS";

    @GetMapping("/accountSettings")
    public ModelAndView getAccountSettings(@PathVariable("consultancy") String consultancyName) {

        try {
            Consultancy consultancy = consultancyService.getByName(consultancyName).get();
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "/accountSettings",
                    consultancyName,
                    "Vereinseinstellungen",
                    PRIVILEGE_NAME
            );
            modelAndView.addObject("consultancyDetails", consultancy.getConsultancyDetails());
            modelAndView.addObject("newField", new CustomDataField());
            modelAndView.addObject("fieldName", "");
            modelAndView.addObject("customDataFields", consultancy.getCustomDataFields());
            modelAndView.setViewName("sites/consultancy/admin/accountSettings");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }

    @PostMapping("/updateIcon")
    public RedirectView updateIcon(
            @PathVariable("consultancy") String consultancyName,
            @Valid ConsultancyDetails newConsultancyDetails,
            RedirectAttributes redirectAttributes
    ) {
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        ConsultancyDetails consultancyDetails = consultancy.getConsultancyDetails();
        consultancyDetails.setIconBase64(newConsultancyDetails.getIconBase64());
        consultancy.setConsultancyDetails(consultancyDetails);
        consultancyService.save(consultancy);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Das Vereinsicon wurde aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/" + consultancyName + "/admin/accountSettings");
    }

    @PostMapping("/updateDetails")
    public RedirectView updateDetails(
            @PathVariable("consultancy") String consultancyName,
            @Valid ConsultancyDetails consultancyDetails,
            RedirectAttributes redirectAttributes
    ) {
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        consultancy.setConsultancyDetails(consultancyDetails);
        consultancyService.save(consultancy);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Die Vereinsdetails wurden aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/" + consultancyDetails.getName() + "/admin/accountSettings");
    }

    @PostMapping("/addCustomField")
    public RedirectView addCustomeField(
            @PathVariable("consultancy") String consultancyName,
            @Valid CustomDataField customDataField,
            RedirectAttributes redirectAttributes
    ) {
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        consultancy.addCustomDataField(customDataField);
        if(customDataField.getRequired()) consultancy.setDataFieldEnforcement();
        consultancyService.save(consultancy);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Das Datenfeld wurde hinzugefügt.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/" + consultancyName + "/admin/accountSettings");
    }

    @PostMapping("/deleteCustomField")
    public RedirectView deleteCustomField(
            @PathVariable("consultancy") String consultancyName,
            @RequestParam String fieldName,
            RedirectAttributes redirectAttributes
    ) {
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        consultancy.deleteCustomDataField(fieldName);
        consultancyService.save(consultancy);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Das Datenfeld wurde gelöscht.",
                "warning"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/" + consultancyName + "/admin/accountSettings");
    }
}
