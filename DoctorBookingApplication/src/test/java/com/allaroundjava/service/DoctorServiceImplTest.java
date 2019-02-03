package com.allaroundjava.service;

import com.allaroundjava.dao.Dao;
import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.model.Doctor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class DoctorServiceImplTest {

    private DoctorDao doctorDao;
    private DoctorService doctorService;

    @Before
    public void setUp() {
        doctorDao = Mockito.mock(DoctorDao.class);
        doctorService = new DoctorServiceImpl(doctorDao);
    }

    @Test
    public void whenAddDoctor_thenPersist() {
        Doctor doctor = new Doctor("Doctor Quinn");
        doctorService.addDoctor(doctor);
        Mockito.verify(doctorDao, times(1)).persist(doctor);
    }
}