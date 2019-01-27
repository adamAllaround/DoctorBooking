package com.allaroundjava.service;

import com.allaroundjava.dao.Dao;
import com.allaroundjava.model.Doctor;

public class DoctorServiceImpl implements DoctorService{
    private Dao<Doctor> doctorDao;

    public DoctorServiceImpl(Dao<Doctor> doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorDao.persist(doctor);
    }
}
