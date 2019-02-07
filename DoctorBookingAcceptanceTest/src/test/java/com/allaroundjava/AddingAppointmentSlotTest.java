package com.allaroundjava;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.JpaConfig;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class, AppConfig.class})
public class AddingAppointmentSlotTest {
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    @Autowired
    private DoctorService doctorService;

    @Test
    public void whenAppointmentSlotsAdded_thenCanBeRetrievedInCorrectTimes() {
        Doctor doctor = new Doctor("Doctor John");
        doctorService.addDoctor(doctor);

        LocalDateTime firstSlotStart = LocalDateTime.of(2019, 1, 30, 10, 0, 0);
        LocalDateTime midPoint = LocalDateTime.of(2019, 1, 30, 10, 30, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                firstSlotStart,
                midPoint);

        LocalDateTime lastSlotEnd = LocalDateTime.of(2019, 1, 30, 11, 0, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                midPoint,
                lastSlotEnd);

        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, firstSlotStart, lastSlotEnd);
        Assert.assertEquals(2, availableSlots.size());
    }
}