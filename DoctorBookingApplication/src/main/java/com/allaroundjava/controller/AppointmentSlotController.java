package com.allaroundjava.controller;

import com.allaroundjava.dto.AppointmentSlotCollectionDto;
import com.allaroundjava.dto.AppointmentSlotDto;
import com.allaroundjava.dto.mapper.AppointmentSlotMapper;
import com.allaroundjava.exception.NotFoundException;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AppointmentSlotController implements SlotsApi {
    private final AppointmentSlotService appointmentSlotService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentSlotController(AppointmentSlotService appointmentSlotService, DoctorService doctorService) {
        this.appointmentSlotService = appointmentSlotService;
        this.doctorService = doctorService;
    }

    public ResponseEntity<AppointmentSlotDto> createSlot(@RequestBody AppointmentSlotDto appointmentSlotInput) {
        Doctor doctor = doctorService.getById(appointmentSlotInput.getDoctorId())
                .orElseThrow(() -> new NotFoundException(String.format("Doctor with ID %d not found", appointmentSlotInput.getDoctorId())));
        AppointmentSlot appointmentSlot = AppointmentSlotMapper.toEntity(appointmentSlotInput, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlot);

        AppointmentSlotDto appointmentSlotDto = AppointmentSlotMapper.toDto(appointmentSlot);
        appointmentSlotDto.add(linkTo(methodOn(AppointmentSlotController.class).getSlotById(appointmentSlot.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentSlotDto);
    }

    public ResponseEntity<AppointmentSlotDto> getSlotById(@PathVariable("id") Long id) {
        AppointmentSlot appointmentSlot = appointmentSlotService.getById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment Slot with id %d not found", id)));
        AppointmentSlotDto appointmentSlotDto = AppointmentSlotMapper.toDto(appointmentSlot);
        appointmentSlotDto.add(linkTo(methodOn(AppointmentSlotController.class).getSlotById(appointmentSlot.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(appointmentSlotDto);
    }

    public ResponseEntity<AppointmentSlotCollectionDto> getSlots(@RequestParam Long doctorId,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Doctor doctor = doctorService.getById(doctorId)
                .orElseThrow(() -> new NotFoundException(String.format("Doctor with ID %d not found", doctorId)));

        List<AppointmentSlot> result = appointmentSlotService.getAppointmentSlotsBetween(doctor, startDate, endDate);

        AppointmentSlotCollectionDto collectionDto = AppointmentSlotMapper.toCollectionDto(result);
        buildHateoasForSlotCollection(doctorId, startDate, endDate, collectionDto);
        return ResponseEntity.status(HttpStatus.OK).body(collectionDto);
    }

    private void buildHateoasForSlotCollection(Long doctorId,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate,
                                               AppointmentSlotCollectionDto collectionDto) {
        for (AppointmentSlotDto dto : collectionDto.getAppointmentSlotDtoList()) {
            dto.add(linkTo(methodOn(AppointmentSlotController.class).getSlotById(dto.getEntityId())).withSelfRel());
        }
        collectionDto.add(linkTo(methodOn(AppointmentSlotController.class).getSlots(doctorId, startDate, endDate)).withSelfRel());
    }
}