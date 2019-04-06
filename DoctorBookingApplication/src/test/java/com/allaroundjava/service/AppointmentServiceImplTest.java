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
import org.mockito.ArgumentCaptor;
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
    public void whenCreateAppointment_thenPersistAndDeleteCalled() {
        Doctor doctor = new Doctor("Doctor Quinn");
        Patient patient = new Patient("James Bond");
        LocalDateTime appointmentStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime appointmentEnd = LocalDateTime.of(2019, 1, 27, 10, 30, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(appointmentStart, appointmentEnd, doctor);
        Mockito.when(appointmentSlotDao.getById(appointmentSlot.getId())).thenReturn(Optional.of(appointmentSlot));
        appointmentService.createAppointment(doctor, patient, appointmentSlot);

        Mockito.verify(appointmentDao, Mockito.times(1)).persist(ArgumentMatchers.any(Appointment.class));
        Mockito.verify(appointmentSlotDao, Mockito.times(1)).persist(ArgumentMatchers.any(AppointmentSlot.class));
        Assert.assertTrue(appointmentSlot.isDeleted());
    }
}