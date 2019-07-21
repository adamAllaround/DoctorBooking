package com.allaroundjava.controller;

import com.allaroundjava.dto.DoctorDto;
import com.allaroundjava.dto.mapper.DoctorDtoMapper;
import com.allaroundjava.exception.NotFoundException;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml", value = "/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable("id") Long id) {
        Optional<Doctor> doctorOptional = doctorService.getById(id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new NotFoundException("Doctor Not Found"));
        DoctorDto doctorDto = DoctorDtoMapper.toDto(doctor);
        doctorDto.add(linkTo(methodOn(DoctorController.class).getDoctor(id)).withSelfRel().withType(MediaType.APPLICATION_XML_VALUE));
        doctorDto.add(linkTo(methodOn(DoctorController.class).getAllDoctors()).withRel("collection").withType(MediaType.APPLICATION_XML_VALUE));
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<DoctorDto> getAllDoctors() {

        return ResponseEntity.status(HttpStatus.OK).body(new DoctorDto());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody  DoctorDto doctorInput) {
        Doctor doctor = DoctorDtoMapper.toEntity(doctorInput);
        doctorService.addDoctor(doctor);
        DoctorDto doctorDto = DoctorDtoMapper.toDto(doctor);
        doctorDto.add(linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDto);
    }
}
