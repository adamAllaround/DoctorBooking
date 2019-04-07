package com.allaroundjava.controller;

import com.allaroundjava.dto.PatientDto;
import com.allaroundjava.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml", value = "/{id}")
    public ResponseEntity<PatientDto> getPatient(Long id) {

    }
}
