package com.allaroundjava.service;

import com.allaroundjava.model.Doctor;

import java.util.Optional;

public interface DoctorService {
    void addDoctor(Doctor doctor);

    Optional<Doctor> getById(Long id);
}
