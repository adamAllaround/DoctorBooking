package com.allaroundjava.service;

import com.allaroundjava.dao.AppointmentDao;
import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = LogManager.getLogger(AppointmentServiceImpl.class);
    private static final String NOT_FOUND = "Appointment Slot not found";
    private final AppointmentDao appointmentDao;
    private final AppointmentSlotDao appointmentSlotDao;

    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao, AppointmentSlotDao appointmentSlotDao) {
        this.appointmentDao = appointmentDao;
        this.appointmentSlotDao = appointmentSlotDao;
    }

    @Override
    public void createAppointment(Doctor doctor, Patient patient, AppointmentSlot appointmentSlot) {
        log.debug("Creating appointment for doctor[id={}] and patient[id={}] at {} to {}", doctor.getId(),
                patient.getId(), appointmentSlot.getStartTime(), appointmentSlot.getEndTime());
        AppointmentSlot slot = appointmentSlotDao.getById(appointmentSlot.getId()).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        slot.setDeleted(true);
        Appointment appointment = new Appointment(doctor, patient, slot.getStartTime(), slot.getEndTime());
        appointmentDao.persist(appointment);
        appointmentSlotDao.persist(slot);
    }
}
