package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.dto.rest.ConsultancyRESTDto;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/consultancies")
public class ConsultancyRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @GetMapping("/{id}")
    public ResponseEntity getConsultancyById(@PathVariable String id) {

        Consultancy consultancy = consultancyService.getById(id)
                .orElseThrow(() -> new ItemNotFoundException("Invalid Item ID"));

        ConsultancyRESTDto consultancyRESTDto = new ConsultancyRESTDto(consultancy);

        return ResponseEntity.ok(consultancyRESTDto);
    }
}
