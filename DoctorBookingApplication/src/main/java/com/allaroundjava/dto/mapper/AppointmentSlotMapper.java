package com.allaroundjava.dto.mapper;

import com.allaroundjava.dto.AppointmentSlotCollectionDto;
import com.allaroundjava.dto.AppointmentSlotDto;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

import java.util.LinkedList;
import java.util.List;

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

    public static AppointmentSlotCollectionDto toCollectionDto(List<AppointmentSlot> appointmentSlotList) {
        AppointmentSlotCollectionDto appointmentSlotCollectionDto = new AppointmentSlotCollectionDto();
        List<AppointmentSlotDto> result = new LinkedList<>();
        appointmentSlotList.forEach(slot -> result.add(toDto(slot)));
        appointmentSlotCollectionDto.setAppointmentSlotDtoList(result);

        return appointmentSlotCollectionDto;
    }

    public static AppointmentSlot toEntity(AppointmentSlotDto appointmentSlotDto, Doctor doctor) {
        return new AppointmentSlot(
                appointmentSlotDto.getStartTime(),
                appointmentSlotDto.getEndTime(),
                doctor
        );
    }
}
