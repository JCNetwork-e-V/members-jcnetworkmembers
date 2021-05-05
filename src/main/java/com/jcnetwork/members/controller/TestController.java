package com.jcnetwork.members.controller;

import com.jcnetwork.members.model.InternalMessage;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.InternalMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Component
@RestController
public class TestController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private InternalMessageService messageService;

    @GetMapping("/test")
    public String test() {

        Consultancy consultancy = consultancyService.getByName("testverein").get();
        InternalMessage message = new InternalMessage(consultancy, consultancy, "TextNachricht", "Dies ist nur eine Testnachricht. Blablabla");
        messageService.sendMessage(message);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/");
        return "test";
    }
}
