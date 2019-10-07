package com.allaroundjava.controller;

import com.allaroundjava.dto.AppointmentDto;
import com.allaroundjava.dto.mapper.AppointmentMapper;
import com.allaroundjava.exception.NotFoundException;
import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
public class AppointmentController implements AppointmentsApi {
    private AppointmentService appointmentService;
    private AppointmentSlotService appointmentSlotService;
    private PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, AppointmentSlotService appointmentSlotService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.appointmentSlotService = appointmentSlotService;
        this.patientService = patientService;

    }

    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        Patient patient = patientService.getById(appointmentDto.getPatientId()).orElseThrow(() -> new NotFoundException("Patient cannot be found"));
        AppointmentSlot appointmentSlot = appointmentSlotService.getById(appointmentDto.getAppointmentSlotId()).orElseThrow(() -> new NotFoundException("Appointment Slot cannot be found"));

        Appointment appointment = appointmentService.createAppointment(patient, appointmentSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.toDto(appointment));
    }
}