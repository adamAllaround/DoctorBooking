package com.allaroundjava.dto.mapper;

import com.allaroundjava.dto.AppointmentDto;
import com.allaroundjava.model.Appointment;

public class AppointmentMapper {
    private AppointmentMapper() {
    }

    public static AppointmentDto toDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setPatientId(appointment.getPatient().getId());
        appointmentDto.setAppointmentSlotId(appointment.getAppointmentSlot().getId());
        return appointmentDto;
    }

}
