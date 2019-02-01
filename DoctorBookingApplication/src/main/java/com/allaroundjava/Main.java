package com.allaroundjava;

import com.allaroundjava.dao.AppointmentDao;
import com.allaroundjava.dao.AppointmentDaoImpl;
import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.dao.AppointmentSlotDaoImpl;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentServiceImpl;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.AppointmentSlotServiceImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DoctorBooking");
        AppointmentDao appointmentDao = new AppointmentDaoImpl(emf);
        AppointmentSlotDao appointmentSlotDao = new AppointmentSlotDaoImpl(emf);
        AppointmentService appointmentService = new AppointmentServiceImpl(appointmentDao, appointmentSlotDao);
        AppointmentSlotService appointmentSlotService = new AppointmentSlotServiceImpl(appointmentSlotDao);

        Doctor doctor = new Doctor("Doctor Quinn");
        Patient patient = new Patient("John James");

        appointmentSlotService.addAppointmentSlot(doctor, LocalDateTime.of(2019, 1, 27, 8, 0, 0),
                LocalDateTime.of(2019,1,27,8,30,0));
        appointmentSlotService.addAppointmentSlot(doctor, LocalDateTime.of(2019, 1, 27, 8, 30, 0),
                LocalDateTime.of(2019,1,27,9,0,0));
        appointmentSlotService.addAppointmentSlot(doctor, LocalDateTime.of(2019, 1, 27, 9, 0, 0),
                LocalDateTime.of(2019,1,27,9,30,0));

        List<AppointmentSlot> slots = appointmentSlotService.getAppointmentSlotsBetween(doctor, LocalDateTime.of(2019, 1, 27, 8, 30, 0),
                LocalDateTime.of(2019, 1, 27, 9, 30, 0));



    }
}
