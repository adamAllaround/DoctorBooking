package com.allaroundjava.service;

import com.allaroundjava.model.Patient;

import java.util.Optional;

public interface PatientService {
    void addPatient(Patient patient);

    Optional<Patient> getById(Long id);
}
