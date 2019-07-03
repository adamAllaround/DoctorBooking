package com.allaroundjava.service;

import com.allaroundjava.dao.AppointmentDao;
import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

public class AppointmentServiceImplTest {

    private AppointmentService appointmentService;
    private AppointmentDao appointmentDao;
    private AppointmentSlotDao appointmentSlotDao;

    @Before
    public void setUp() {
        appointmentDao = Mockito.mock(AppointmentDao.class);
        appointmentSlotDao = Mockito.mock(AppointmentSlotDao.class);
        appointmentService = new AppointmentServiceImpl(appointmentDao, appointmentSlotDao);
    }

    @Test
    public void whenCreateAppointment_thenSlotIsDeletedAndApointmentPersisted() {
        Doctor doctor = new Doctor("Doctor Quinn");
        Patient patient = new Patient("James Bond");
        AppointmentSlot appointmentSlot = createAppointmentSlot(doctor);
        Mockito.when(appointmentSlotDao.getAvailableById(appointmentSlot.getId())).thenReturn(Optional.of(appointmentSlot));
        appointmentService.createAppointment(patient, appointmentSlot);

        Mockito.verify(appointmentDao, Mockito.times(1)).persist(ArgumentMatchers.any(Appointment.class));
        Mockito.verify(appointmentSlotDao, Mockito.times(1)).persist(ArgumentMatchers.any(AppointmentSlot.class));
        Assert.assertTrue(appointmentSlot.isDeleted());
    }

    private AppointmentSlot createAppointmentSlot(Doctor doctor) {
        LocalDateTime appointmentStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime appointmentEnd = LocalDateTime.of(2019, 1, 27, 10, 30, 0);
        return new AppointmentSlot(appointmentStart, appointmentEnd, doctor);
    }

    @Test
    public void whenBookingAlreadyBookedSlot_thenException() {

    }
}