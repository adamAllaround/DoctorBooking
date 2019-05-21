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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class AppointmentSlotController {
    private final AppointmentSlotService appointmentSlotService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentSlotController(AppointmentSlotService appointmentSlotService, DoctorService doctorService) {
        this.appointmentSlotService = appointmentSlotService;
        this.doctorService = doctorService;
    }

    @PostMapping(produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<AppointmentSlotDto> createSlot(@RequestBody AppointmentSlotDto appointmentSlotInput) {
        Doctor doctor = doctorService.getById(appointmentSlotInput.getDoctorId())
                .orElseThrow(() -> new NotFoundException(String.format("Doctor with ID %d not found", appointmentSlotInput.getDoctorId())));
        AppointmentSlot appointmentSlot = AppointmentSlotMapper.toEntity(appointmentSlotInput, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentSlotMapper.toDto(appointmentSlot));
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<AppointmentSlotDto> getSlotById(@PathVariable("id") Long id) {
        AppointmentSlot appointmentSlot = appointmentSlotService.getById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment Slot with id %d not found", id)));
        return ResponseEntity.status(HttpStatus.OK).body(AppointmentSlotMapper.toDto(appointmentSlot));
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AppointmentSlotCollectionDto> getSlots(@RequestParam Long doctorId,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Doctor doctor = doctorService.getById(doctorId)
                .orElseThrow(() -> new NotFoundException(String.format("Doctor with ID %d not found", doctorId)));

        List<AppointmentSlot> result = appointmentSlotService.getAppointmentSlotsBetween(doctor, startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(AppointmentSlotMapper.toCollectionDto(result));
    }
}
