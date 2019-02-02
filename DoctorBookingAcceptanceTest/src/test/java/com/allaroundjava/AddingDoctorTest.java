package com.allaroundjava;

import com.allaroundjava.dao.Dao;
import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.dao.DoctorDaoImpl;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.DoctorService;
import com.allaroundjava.service.DoctorServiceImpl;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AddingDoctorTest {
    private EntityManagerFactory emf;
    private DoctorDao doctorDao;
    private DoctorService doctorService;

    public AddingDoctorTest() {
        this.emf = Persistence.createEntityManagerFactory("DoctorBooking");
        doctorDao = new DoctorDaoImpl(emf);
        doctorService = new DoctorServiceImpl(doctorDao);
    }

    @Test
    public void whenAddingDoctor_thenNoException() {
        Doctor doctor = new Doctor("Doctor Quinn");
        doctorService.addDoctor(doctor);
    }
}
