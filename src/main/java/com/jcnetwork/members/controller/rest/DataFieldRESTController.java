package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.service.ConsultancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/{consultancy}/dataFields")
public class DataFieldRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity deleteMessage(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("name") String fieldName) throws Exception {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new Exception("Consultancy not found"));

        consultancy.deleteCustomDataField(fieldName);
        consultancyService.save(consultancy);

        return ResponseEntity.ok("Data field " + fieldName + " deleted");
    }
}
