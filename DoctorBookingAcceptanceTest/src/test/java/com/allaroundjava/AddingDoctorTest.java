package com.allaroundjava;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.DoctorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class, AppConfig.class})
public class AddingDoctorTest {
    @Autowired
    private DoctorService doctorService;

    @Test
    public void whenAddingDoctor_thenNoException() {
        Doctor doctor = new Doctor("Doctor Quinn");
        doctorService.addDoctor(doctor);
    }
}
