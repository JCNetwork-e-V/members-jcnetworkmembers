package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.mapper.OrganizationalEntityMapper;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.OrganizationalEntity;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/organizationalStructure")
public class OrganizationalStructureRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private OrganizationalEntityMapper mapper;

    @GetMapping(path = "/{consultancy}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOrganizationalStructur(@PathVariable("consultancy") String consultancyName) {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Consultancy not found"));

        return ResponseEntity.ok(mapper.toDto(consultancy.getRootEntity()));
    }

    @PutMapping(path = "/{consultancy}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOrganizationalStructure(
            @PathVariable("consultancy") String consultancyName,
            @RequestBody OrganizationalEntity entity) {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Consultancy not found"));

        consultancy.setRootEntity(entity);
        consultancyService.save(consultancy);
        return ResponseEntity.ok(entity);
    }
}
