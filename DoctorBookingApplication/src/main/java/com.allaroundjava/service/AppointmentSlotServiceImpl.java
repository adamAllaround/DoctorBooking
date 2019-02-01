package com.allaroundjava.service;

import com.allaroundjava.dao.AppointmentSlotDao;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentSlotServiceImpl implements AppointmentSlotService {
    private AppointmentSlotDao appointmentSlotDao;

    public AppointmentSlotServiceImpl(AppointmentSlotDao appointmentSlotDao) {
        this.appointmentSlotDao = appointmentSlotDao;
    }

    @Override
    public void addAppointmentSlot(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        AppointmentSlot appointmentSlot = new AppointmentSlot(startTime, endTime, doctor);
        appointmentSlotDao.persist(appointmentSlot);
    }

    @Override
    public List<AppointmentSlot> getAppointmentSlotsBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        return appointmentSlotDao.getAppointmentSlotsBetween(doctor, startTime, endTime);
    }
}
