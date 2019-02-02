package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;

import javax.persistence.EntityManagerFactory;

public class DoctorDaoImpl extends BaseDao<Doctor> implements DoctorDao {
    public DoctorDaoImpl(EntityManagerFactory emf) {
        super(Doctor.class, emf);
    }
}
