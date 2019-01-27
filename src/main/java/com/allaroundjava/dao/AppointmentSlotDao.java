package com.allaroundjava.dao;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentSlotDao extends Dao<AppointmentSlot> {
    List<AppointmentSlot> getAppointmentSlotsBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
}
