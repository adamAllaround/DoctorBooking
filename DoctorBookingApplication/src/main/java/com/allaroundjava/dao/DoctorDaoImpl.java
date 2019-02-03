package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public class DoctorDaoImpl extends BaseDao<Doctor> implements DoctorDao {
    public DoctorDaoImpl() {
        super(Doctor.class);
    }
}
