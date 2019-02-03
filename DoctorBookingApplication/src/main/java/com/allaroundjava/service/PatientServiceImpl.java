package com.allaroundjava.service;

import com.allaroundjava.dao.PatientDao;
import com.allaroundjava.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
