package com.allaroundjava;

import com.allaroundjava.dao.AppointmentDao;
import com.allaroundjava.dao.AppointmentDaoImpl;
import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.dao.AppointmentSlotDaoImpl;
import com.allaroundjava.dao.Dao;
import com.allaroundjava.dao.DoctorDao;
import com.allaroundjava.dao.DoctorDaoImpl;
import com.allaroundjava.dao.PatientDaoImpl;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentServiceImpl;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.AppointmentSlotServiceImpl;
import com.allaroundjava.service.DoctorService;
import com.allaroundjava.service.DoctorServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  This is an acceptance test for the following user story
 *  * As Patient I can Book given doctor at a given time
 *  ** Only available slot can be booked
 *  ** When slot is booked an appointment is registered
 *  ** When slot is booked, the slot is marked as removed *
 */

public class BookingAppointmentTest {
    private EntityManagerFactory emf;
    private AppointmentSlotService appointmentSlotService;
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private AppointmentSlotDao appointmentSlotDao;
    private AppointmentDao appointmentDao;
    private DoctorDao doctorDao;
    private Dao<Patient> patientDao;

    public BookingAppointmentTest() {
        this.emf = Persistence.createEntityManagerFactory("DoctorBooking");
        this.appointmentSlotDao = new AppointmentSlotDaoImpl(emf);
        this.doctorDao = new DoctorDaoImpl(emf);
        this.patientDao = new PatientDaoImpl(emf);
        this.appointmentDao = new AppointmentDaoImpl(emf);
        this.doctorService = new DoctorServiceImpl(doctorDao);
        this.appointmentSlotService = new AppointmentSlotServiceImpl(appointmentSlotDao);
        this.appointmentService = new AppointmentServiceImpl(appointmentDao, appointmentSlotDao);
    }

    @Test
    public void whenSlotAvailable_thenItCanBeBooked() {
        Doctor doctor = new Doctor("Doctor Henry");
        doctorService.addDoctor(doctor);
        Patient patient = new Patient("Patient John");
        patientDao.persist(patient);

        LocalDateTime firstSlotStart = LocalDateTime.of(2019, 1, 28, 12, 0, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                firstSlotStart,
                LocalDateTime.of(2019, 1, 30, 13, 0, 0));

        LocalDateTime lastSlotend = LocalDateTime.of(2019, 1, 28, 13, 30, 0);
        appointmentSlotService.addAppointmentSlot(doctor,
                LocalDateTime.of(2019, 1, 28, 13, 0, 0),
                lastSlotend);

        List<AppointmentSlot> availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor,
                LocalDateTime.of(2019,1,28,10,0,0),
                LocalDateTime.of(2019,1,28,13,20,0));
        Assert.assertEquals(2, availableSlots.size());
        AppointmentSlot appointmentSlot = availableSlots.get(0);

        appointmentService.createAppointment(doctor, patient, appointmentSlot);

        availableSlots = appointmentSlotService.getAppointmentSlotsBetween(doctor,
                LocalDateTime.of(2019,1,28,10,0,0),
                LocalDateTime.of(2019,1,28,13,20,0));
        Assert.assertEquals(1, availableSlots.size());
    }
}
