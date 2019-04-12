package com.allaroundjava.service;

import com.allaroundjava.dao.PatientDao;
import com.allaroundjava.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public void addPatient(Patient patient) {
        patientDao.persist(patient);
    }

    @Override
    public Optional<Patient> getById(Long id) {
        return patientDao.getById(id);
    }
}
