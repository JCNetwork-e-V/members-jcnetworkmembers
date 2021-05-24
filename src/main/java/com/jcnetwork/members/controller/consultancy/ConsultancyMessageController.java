package com.jcnetwork.members.controller.consultancy;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.InternalMessage;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.NewInternalMessageDto;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.InternalMessageService;
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

@Controller
@RequestMapping("/{consultancy}/admin")
public class ConsultancyMessageController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private InternalMessageService internalMessageService;

    private final String PRIVILEG_NAME = "MESSAGES";

    @GetMapping("/messages")
    public ModelAndView getMessages(@PathVariable("consultancy") String consultancyName) {

        try {
            ModelAndView modelAndView = utils.createLayoutConsultancy(
                    "/messages",
                    consultancyName,
                    "Nachrichten",
                    PRIVILEG_NAME
            );

            modelAndView.addObject("newMessage", new NewInternalMessageDto());
            modelAndView.setViewName("sites/consultancy/admin/messages");
            return modelAndView;
        } catch (Exception e){
            return new ModelAndView(new RedirectView("/accessForbidden"));
        }
    }

    @PostMapping("/sendMessage")
    public RedirectView sendMessage(
            @Valid NewInternalMessageDto messageDto,
            @PathVariable("consultancy") String consultancyName
    ) {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Consultancy not found"));

        for(String recipientId : messageDto.getRecipientIds()){
            InternalMessage message = new InternalMessage(
                    recipientId,
                    consultancy,
                    messageDto.getSubject(),
                    messageDto.getBody()
            );
            internalMessageService.createMessage(message);
        }

        return new RedirectView("messages");
    }
}
