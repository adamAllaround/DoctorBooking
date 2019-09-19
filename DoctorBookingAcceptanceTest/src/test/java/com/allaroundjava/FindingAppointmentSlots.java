package com.allaroundjava;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class, AppConfig.class})
public class FindingAppointmentSlots {
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    @Autowired
    private DoctorService doctorService;
    private Doctor doctor;

    @Before
    public void setUp() {
        this.doctor = createDoctor();
        createAppointmentSlot(doctor);
    }

    private Doctor createDoctor() {
        Doctor doctor = new Doctor("Henry Fonda");
        doctorService.addDoctor(doctor);
        return doctor;
    }

    private void createAppointmentSlot(Doctor doctor) {
        LocalDateTime slotStart = LocalDateTime.of(2019, 1, 27, 13, 0, 0);
        LocalDateTime slotEnd = LocalDateTime.of(2019, 1, 27, 15, 0, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(slotStart, slotEnd, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlot);
    }

    @Test
    public void whenPeriodOutsideOfSlot_thenNotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 10, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());
    }

    @Test
    public void whenSlotInsidePeriod_thenSlotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 15, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());
    }

    @Test
    public void whenSlotPartiallyInPeriod_thenSlotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 14, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());

        periodStart = LocalDateTime.of(2019, 1, 27, 14, 0, 0);
        periodEnd = LocalDateTime.of(2019, 1, 27, 16, 30, 0);
        availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());
    }

    @Test
    public void whenSlotTouchingPeriodOutside_thenNotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 13, 0, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());

        periodStart = LocalDateTime.of(2019, 1, 27, 15, 0, 0);
        periodEnd = LocalDateTime.of(2019, 1, 27, 15, 30, 0);
        availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());
    }
}