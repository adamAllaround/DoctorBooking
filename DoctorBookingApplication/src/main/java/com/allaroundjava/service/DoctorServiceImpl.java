package com.allaroundjava.service;

import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{
    private final DoctorDao doctorDao;

    @Autowired
    public DoctorServiceImpl(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorDao.persist(doctor);
    }
}
