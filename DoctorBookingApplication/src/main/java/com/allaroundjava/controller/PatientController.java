package com.allaroundjava.controller;

import com.allaroundjava.dto.PatientDto;
import com.allaroundjava.dto.mapper.PatientDtoMaper;
import com.allaroundjava.exception.NotFoundException;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final String PATIENT_NOT_FOUND = "Patient not found";
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml", value = "/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable("id") Long id) {
        Patient patient = patientService.getById(id).orElseThrow(() -> new NotFoundException(PATIENT_NOT_FOUND));
        PatientDto patientDto = PatientDtoMaper.toDto(patient);
        patientDto.add(linkTo(methodOn(PatientController.class).getPatient(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(patientDto);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        Patient patient = PatientDtoMaper.toEntity(patientDto);
        patientService.addPatient(patient);
        patientDto.add(linkTo(methodOn(PatientController.class).getPatient(patient.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDto);
    }

}
