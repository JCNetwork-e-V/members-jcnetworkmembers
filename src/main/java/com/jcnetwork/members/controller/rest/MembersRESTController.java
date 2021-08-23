package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.mapper.MemberMapper;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{consultancy}/members")
public class MembersRESTController {

    @Autowired
    private MemberMapper mapper;

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("all")
    public ResponseEntity getMembers(@PathVariable("consultancy") String consultancyName) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));
        return ResponseEntity.ok(mapper.toDto(consultancy.getMembers()));
    }

    @GetMapping("{id}")
    public ResponseEntity getMember(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("id") String id
    ) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));
        return ResponseEntity.ok(mapper.toDto(consultancy.getMemberById(id)));
    }
}
