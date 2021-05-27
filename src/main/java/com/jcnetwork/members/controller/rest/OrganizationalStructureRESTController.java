package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.mapper.OrganizationalEntityMapper;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.Member;
import com.jcnetwork.members.model.data.consultancy.OrganizationalEntity;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/organizationalStructure")
public class OrganizationalStructureRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private OrganizationalEntityMapper mapper;

    @GetMapping(path = "/{consultancy}/allEntities", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(path = "/{consultancy}/{organizationalEntity}/updateMembers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEntityMembers(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("organizationalEntity") String organizationalEntity,
            @RequestBody List<String> memberEmails) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));

        List<Member> updatedMembers = new ArrayList<>();

        for(Member member : consultancy.getMembers()) {
            if(memberEmails.contains(member.getEmail())){
                if(!member.getOrganizationalEntities().contains(organizationalEntity)) {
                    member.addOrganizationalEntity(organizationalEntity);
                }
            } else {
                member.removeOrganizationalEntity(organizationalEntity);
            }
            updatedMembers.add(member);
        }
        consultancy.setMembers(updatedMembers);
        consultancyService.save(consultancy);
        return ResponseEntity.ok(consultancy);
    }
}
