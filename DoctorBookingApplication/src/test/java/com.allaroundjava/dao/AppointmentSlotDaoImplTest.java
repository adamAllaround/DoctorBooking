package com.allaroundjava.dao;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentSlotDaoImplTest {
    private EntityManagerFactory emf;
    private AppointmentSlotDao appointmentSlotDao;
    private DoctorDao doctorDao;
    private Doctor doctor;
    private AppointmentSlot appointmentSlot;

    public AppointmentSlotDaoImplTest() {
        this.emf = Persistence.createEntityManagerFactory("DoctorBooking");
        this.appointmentSlotDao = new AppointmentSlotDaoImpl(emf);
        this.doctorDao = new DoctorDao(emf);
        this.doctor = createDoctor();
        this.appointmentSlot = createAppointmentSlot(doctor);
    }

    private Doctor createDoctor() {
        Doctor doctor = new Doctor("Henry Fonda");
        doctorDao.persist(doctor);
        return doctor;
    }

    private AppointmentSlot createAppointmentSlot(Doctor doctor) {
        LocalDateTime slotStart = LocalDateTime.of(2019, 1, 27, 13, 0, 0);
        LocalDateTime slotEnd = LocalDateTime.of(2019, 1, 27, 15, 0, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(slotStart, slotEnd, doctor);
        appointmentSlotDao.persist(appointmentSlot);
        return appointmentSlot;
    }

    @Test
    public void whenPeriodOutsideOfSlot_thenNotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 10, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());
    }

    @Test
    public void whenSlotInsidePeriod_thenSlotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 15, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());
    }

    @Test
    public void whenSlotPartiallyInPeriod_thenSlotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 14, 30, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());

        periodStart = LocalDateTime.of(2019, 1, 27, 14, 0, 0);
        periodEnd = LocalDateTime.of(2019, 1, 27, 16, 30, 0);
        availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(1, availableSlots.size());
    }

    @Test
    public void whenSlotTouchingPeriodOutside_thenNotReturned() {
        LocalDateTime periodStart = LocalDateTime.of(2019, 1, 27, 10, 0, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2019, 1, 27, 13, 0, 0);
        List<AppointmentSlot> availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());

        periodStart = LocalDateTime.of(2019, 1, 27, 15, 0, 0);
        periodEnd = LocalDateTime.of(2019, 1, 27, 15, 30, 0);
        availableSlots = appointmentSlotDao.getAppointmentSlotsBetween(doctor, periodStart, periodEnd);
        Assert.assertEquals(0, availableSlots.size());
    }
}