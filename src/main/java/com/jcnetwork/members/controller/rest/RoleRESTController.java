package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.mapper.RoleMapper;
import com.jcnetwork.members.mapper.UserDetailsMapper;
import com.jcnetwork.members.model.data.*;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/{consultancy}/roles")
public class RoleRESTController {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping(path = "/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoleByName(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("role") String roleName) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));

        for(Role role : consultancy.getRoles()) {
            if(role.getName().equals(roleName)) {
                return ResponseEntity.ok(mapper.toDto(role));
            }
        }
        throw new Exception("Role not found");
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoleByName(
            @PathVariable("consultancy") String consultancyName) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));

        return ResponseEntity.ok(consultancy.getRoles());
    }

    @PutMapping(path = "/{role}/updateBearers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRoles(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("role") String roleName,
            @RequestBody List<String> memberEmails) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));

        List<Member> updatedMembers = new ArrayList<>();

        for(Member member : consultancy.getMembers()) {
            if(memberEmails.contains(member.getEmail())){
                if(!member.getRoles().contains(roleName)) {
                    member.addRole(roleName);
                }
            } else {
                member.removeRole(roleName);
            }
            updatedMembers.add(member);
        }
        consultancy.setMembers(updatedMembers);
        consultancyService.save(consultancy);
        return ResponseEntity.ok("Roles saved");
    }
}
