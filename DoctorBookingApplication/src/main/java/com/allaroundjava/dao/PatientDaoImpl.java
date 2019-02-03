package com.allaroundjava.dao;

import com.allaroundjava.model.Patient;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDaoImpl extends BaseDao<Patient> implements PatientDao {
    public PatientDaoImpl() {
        super(Patient.class);
    }
}
