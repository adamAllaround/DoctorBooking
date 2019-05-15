package com.allaroundjava.service;

import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;

public class AppointmentSlotServiceImplTest {
    private AppointmentSlotDao appointmentSlotDao;
    private AppointmentSlotService appointmentSlotService;

    @Before
    public void setUp() {
        this.appointmentSlotDao = Mockito.mock(AppointmentSlotDao.class);
        this.appointmentSlotService = new AppointmentSlotServiceImpl(appointmentSlotDao);
    }

    @Test
    public void whenAddAvailabilityPeriod_thenPersistCalled() {
        Doctor doctor = new Doctor("Doctor Quinn");
        LocalDateTime startTime = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2019, 1, 27, 10, 30, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(startTime, endTime, doctor);
        appointmentSlotService.addAppointmentSlot(appointmentSlot);
        Mockito.verify(appointmentSlotDao, times(1)).persist(appointmentSlot);
    }
}