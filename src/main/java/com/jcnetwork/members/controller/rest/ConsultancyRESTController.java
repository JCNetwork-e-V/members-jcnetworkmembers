package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.mapper.ConsultancyMapper;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/consultancies")
public class ConsultancyRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private ConsultancyMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity getConsultancyById(@PathVariable String id) {

        Consultancy consultancy = consultancyService.getById(id)
                .orElseThrow(() -> new ItemNotFoundException("Invalid Item ID"));

        return ResponseEntity.ok(mapper.toDto(consultancy));
    }
}
