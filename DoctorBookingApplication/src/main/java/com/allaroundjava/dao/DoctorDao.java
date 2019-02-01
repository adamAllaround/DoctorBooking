package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;

import javax.persistence.EntityManagerFactory;

public class DoctorDao extends BaseDao<Doctor> {
    public DoctorDao(EntityManagerFactory emf) {
        super(Doctor.class, emf);
    }
}
