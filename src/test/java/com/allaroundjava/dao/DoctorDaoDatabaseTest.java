package com.allaroundjava.dao;

import com.allaroundjava.model.Doctor;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DoctorDaoDatabaseTest {
    private EntityManagerFactory emf;
    private Dao<Doctor> doctorDao;

    public DoctorDaoDatabaseTest() {
        this.emf = Persistence.createEntityManagerFactory("DoctorBooking");
        doctorDao = new DoctorDao(emf);
    }

    @Test
    public void whenPersist_thenNoException() {
        Doctor doctor = new Doctor("Doctor Quinn");
        doctorDao.persist(doctor);
    }
}