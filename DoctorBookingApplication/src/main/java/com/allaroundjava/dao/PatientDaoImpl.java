package com.allaroundjava.dao;

import com.allaroundjava.model.Patient;

import javax.persistence.EntityManagerFactory;

public class PatientDaoImpl extends BaseDao<Patient> implements PatientDao {
    public PatientDaoImpl(EntityManagerFactory emf) {
        super(Patient.class, emf);
    }
}
