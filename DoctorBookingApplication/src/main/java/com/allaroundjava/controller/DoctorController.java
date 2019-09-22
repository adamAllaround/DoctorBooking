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
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/doctors")
public class DoctorController implements DoctorsApi {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE, value = "/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable("id") Long id) {
        Optional<Doctor> doctorOptional = doctorService.getById(id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new NotFoundException("Doctor Not Found"));
        DoctorDto doctorDto = DoctorDtoMapper.toDto(doctor);
        doctorDto.add(linkTo(methodOn(DoctorController.class).getDoctor(id)).withSelfRel().withType(MediaType.APPLICATION_XML_VALUE));
        doctorDto.add(linkTo(methodOn(DoctorController.class).getAllDoctors()).withRel("collection").withType(MediaType.APPLICATION_XML_VALUE));
        return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorInput) {
        Doctor doctor = DoctorDtoMapper.toEntity(doctorInput);
        doctorService.addDoctor(doctor);
        DoctorDto doctorDto = DoctorDtoMapper.toDto(doctor);
        doctorDto.add(linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDto);
    }

    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Collections.emptyList());
    }
}
