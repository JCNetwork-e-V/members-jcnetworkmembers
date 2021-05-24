package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/entity")
public class EntityRESTController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping(path="/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUserEntityDetails() {

        List<EntityDetailsDto> entities = new ArrayList<>();
        entities.addAll(userService.getAllAsEntity());

        return ResponseEntity.ok(entities);
    }

    @GetMapping(path="/consultancy/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllConsultancyEntityDetails() {

        List<EntityDetailsDto> entities = new ArrayList<>();
        entities.addAll(consultancyService.getAllAsEntity());

        return ResponseEntity.ok(entities);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllEntityDetails() {

        List<EntityDetailsDto> entities = new ArrayList<>();
        entities.addAll(userService.getAllAsEntity());
        entities.addAll(consultancyService.getAllAsEntity());

        return ResponseEntity.ok(entities);
    }
}
