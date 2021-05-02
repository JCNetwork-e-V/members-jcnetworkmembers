package com.jcnetwork.members.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping("/accessForbidden")
    public String getForbiddenErrorPage() {
        return "sites/error/error";
    }
}
