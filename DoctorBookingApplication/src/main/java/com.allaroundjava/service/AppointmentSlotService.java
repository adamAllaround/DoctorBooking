package com.allaroundjava.service;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentSlotService {
    void addAppointmentSlot(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);

    List<AppointmentSlot> getAppointmentSlotsBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
}
