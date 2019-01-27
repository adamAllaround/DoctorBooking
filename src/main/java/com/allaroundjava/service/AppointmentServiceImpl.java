package com.allaroundjava.service;

import com.allaroundjava.dao.AppointmentDao;
import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;

public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentDao appointmentDao;
    private AppointmentSlotDao appointmentSlotDao;

    public AppointmentServiceImpl(AppointmentDao appointmentDao, AppointmentSlotDao appointmentSlotDao) {
        this.appointmentDao = appointmentDao;
        this.appointmentSlotDao = appointmentSlotDao;
    }

    @Override
    public void createAppointment(Doctor doctor, Patient patient, AppointmentSlot appointmentSlot) {
        Appointment appointment = new Appointment(doctor, patient, appointmentSlot.getStartTime(), appointmentSlot.getEndTime());
        appointmentDao.persist(appointment);
        appointmentSlotDao.delete(appointmentSlot);
    }
}
