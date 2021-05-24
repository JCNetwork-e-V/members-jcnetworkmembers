package com.jcnetwork.members.controller;

import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.InternalMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class TestController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private InternalMessageService messageService;

    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
