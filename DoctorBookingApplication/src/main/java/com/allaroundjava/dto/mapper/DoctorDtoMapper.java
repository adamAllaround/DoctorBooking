package com.allaroundjava.dto.mapper;

import com.allaroundjava.dto.DoctorDto;
import com.allaroundjava.model.Doctor;

public final class DoctorDtoMapper {
    private DoctorDtoMapper() {
    }

    public static DoctorDto toDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setEntityId(doctor.getId());
        doctorDto.setName(doctor.getName());
        return doctorDto;
    }

    public static Doctor toEntity(DoctorDto doctorInput) {
        return new Doctor(doctorInput.getName());
    }
}
