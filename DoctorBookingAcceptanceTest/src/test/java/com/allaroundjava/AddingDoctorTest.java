package com.allaroundjava;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.JpaConfig;
import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.dao.DoctorDaoImpl;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.DoctorService;
import com.allaroundjava.service.DoctorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class, AppConfig.class})
public class AddingDoctorTest {
    @Autowired
    private DoctorService doctorService;

    @Test
    public void whenAddingDoctor_thenNoException() {
        Doctor doctor = new Doctor("Doctor Quinn");
        doctorService.addDoctor(doctor);
    }
}
