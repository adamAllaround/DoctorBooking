package com.allaroundjava;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.JpaConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import com.allaroundjava.service.PatientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is an acceptance test for the following user story
 * * As Patient I can Book given doctor at a given time
 * ** Only available slot can be booked
 * ** When slot is booked an appointment is registered
 * ** When slot is booked, the slot is marked as removed *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class, AppConfig.class})
public class BookingAppointmentTest {
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    @Test
    public void whenSlotAvailable_thenItCanBeBooked() {
        Doctor doctor = new Doctor("Doctor Henry");
        doctorService.addDoctor(doctor);
        Patient patient = new Patient("Patient John");
        patientService.addPatient(patient);

        LocalDateTime firstSlotStart = LocalDateTime.of(2019, 1, 28, 12, 0, 0);
        LocalDateTime firstSlotEnd = LocalDateTime.of(2019, 1, 30, 13, 0, 0);
        AppointmentSlot appointmentSlotFirst = new AppointmentSlot(firstSlotStart, firstSlotEnd, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlotFirst);

        LocalDateTime lastSlotStart = LocalDateTime.of(2019, 1, 28, 13, 0, 0);
        LocalDateTime lastSlotEnd = LocalDateTime.of(2019, 1, 28, 13, 30, 0);
        AppointmentSlot appointmentSlotLast = new AppointmentSlot(lastSlotStart, lastSlotEnd, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlotLast);

        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor,
                LocalDateTime.of(2019, 1, 28, 10, 0, 0),
                LocalDateTime.of(2019, 1, 28, 13, 20, 0));
        Assert.assertEquals(2, availableSlots.size());
        AppointmentSlot appointmentSlot = availableSlots.get(0);

        appointmentService.createAppointment(patient, appointmentSlot);

        availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor,
                LocalDateTime.of(2019, 1, 28, 10, 0, 0),
                LocalDateTime.of(2019, 1, 28, 13, 20, 0));
        Assert.assertEquals(1, availableSlots.size());

        Assert.assertTrue(appointmentSlotService.getById(appointmentSlot.getId()).get().isDeleted());
    }
}
