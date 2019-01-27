package com.allaroundjava.dao;

import com.allaroundjava.model.Patient;

import javax.persistence.EntityManagerFactory;

public class PatientDao extends BaseDao<Patient> {
    protected PatientDao(EntityManagerFactory emf) {
        super(Patient.class, emf);
    }
}
