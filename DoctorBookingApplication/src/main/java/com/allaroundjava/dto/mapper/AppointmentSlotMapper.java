package com.allaroundjava.dto.mapper;

import com.allaroundjava.dto.AppointmentSlotDto;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

public class AppointmentSlotMapper {
    private AppointmentSlotMapper() {
    }

    public static AppointmentSlotDto toDto(AppointmentSlot appointmentSlot) {
        AppointmentSlotDto dto = new AppointmentSlotDto();
        dto.setId(appointmentSlot.getId());
        dto.setStartTime(appointmentSlot.getStartTime());
        dto.setEndTime(appointmentSlot.getEndTime());
        dto.setDoctorId(appointmentSlot.getDoctor().getId());
        return dto;
    }

    public static AppointmentSlot toEntity(AppointmentSlotDto appointmentSlotDto, Doctor doctor) {
        return new AppointmentSlot(
                appointmentSlotDto.getStartTime(),
                appointmentSlotDto.getEndTime(),
                doctor
        );
    }
}
