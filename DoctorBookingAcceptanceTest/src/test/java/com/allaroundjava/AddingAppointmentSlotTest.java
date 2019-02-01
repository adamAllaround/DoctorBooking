package com.allaroundjava;

import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.dao.AppointmentSlotDaoImpl;
import com.allaroundjava.dao.Dao;
import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.AppointmentSlotServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class AddingAppointmentSlotTest {
    private EntityManagerFactory emf;
    private AppointmentSlotService appointmentSlotService;
    private AppointmentSlotDao appointmentSlotDao;
    private Dao<Doctor> doctorDao;

    public AddingAppointmentSlotTest() {
        this.emf = Persistence.createEntityManagerFactory("DoctorBooking");
        appointmentSlotDao = new AppointmentSlotDaoImpl(emf);
        doctorDao = new DoctorDao(emf);
        appointmentSlotService = new AppointmentSlotServiceImpl(appointmentSlotDao);
    }

    @Test
    public void whenAppointmentSlotsAdded_thenCanBeRetrievedInCorrectTimes() {
        Doctor doctor = new Doctor("Doctor John");
        doctorDao.persist(doctor);

        LocalDateTime firstSlotStart = LocalDateTime.of(2019, 1, 30, 10, 0, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                firstSlotStart,
                LocalDateTime.of(2019, 1, 30, 10, 30, 0));

        LocalDateTime lastSlotend = LocalDateTime.of(2019, 1, 30, 11, 0, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                LocalDateTime.of(2019, 1, 30, 10, 30, 0),
                lastSlotend);

        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor, firstSlotStart, lastSlotend);
        Assert.assertEquals(2, availableSlots.size());
    }
}