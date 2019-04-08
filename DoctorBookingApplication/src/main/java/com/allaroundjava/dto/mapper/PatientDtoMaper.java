package com.allaroundjava.dto.mapper;

import com.allaroundjava.dto.PatientDto;
import com.allaroundjava.model.Patient;

public class PatientDtoMaper {
    public static PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setName(patient.getName());
        return patientDto;
    }

    public static Patient toEntity(PatientDto patientDto) {
        return new Patient(patientDto.getName());
    }
}
