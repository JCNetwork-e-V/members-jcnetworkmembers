package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.CustomDataField;
import com.jcnetwork.members.model.data.consultancy.Member;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.utils.ControllerUtils;
import com.jcnetwork.members.utils.DataCompletionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class DataCompletionController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("/{consultancy}/dataCompletion")
    public ModelAndView getDataCompletionForm(
            @PathVariable("consultancy") String consultancyName,
            @RequestParam(required = false) String url
    ) throws Exception {

        Map<String, Object> defaultValues = Map.of(
                "Text", "",
                "Nummer", 0,
                "Datum", new Date()
        );

        Map<String, Object> openDataFields = new HashMap<>();
        User user = utils.getUserFromContext();

        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        Member member = consultancy.getMemberByUser(user);
        for (CustomDataField dataField : consultancy.getCustomDataFields().values()) {
            if(dataField.getRequired()){
                if(member.getCustomFields() == null || !member.getCustomFields().containsKey(dataField.getName())) {
                    openDataFields.put(dataField.getName(), defaultValues.get(dataField.getType()));
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();

        if(openDataFields.size() != 0){
            DataCompletionForm dataCompletionForm = new DataCompletionForm();
            dataCompletionForm.setOpenDataFields(openDataFields);

            modelAndView.addObject("ConsultancyName", consultancy.getConsultancyDetails().getName());
            modelAndView.addObject("form", dataCompletionForm);
            modelAndView.addObject("originalUrl", url);
            modelAndView.setViewName("sites/user/dataCompletion");
        } else {
            if(url == null || url.isEmpty()) url = "/home";
            member.setHasNewDataField(false);
            consultancyService.save(consultancy);
            modelAndView.setView(new RedirectView(url));
        }
        return modelAndView;
    }

    @PostMapping("/{consultancy}/dataCompletion")
    public RedirectView dataCompletion(
            @PathVariable("consultancy") String consultancyName,
            @ModelAttribute("form") DataCompletionForm form,
            @RequestParam(required = false) String url
    ) throws Exception {

        User user = utils.getUserFromContext();
        Consultancy consultancy = consultancyService.getByName(consultancyName).get();
        Member member = consultancy.getMemberByUser(user);
        member.addCustomFields(form.getOpenDataFields());
        member.setHasNewDataField(false);
        consultancyService.save(consultancy);

        if(url == null || url.isEmpty()) url = "/home";
        return new RedirectView(url);
    }
}
