package com.allaroundjava.service;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;

public interface AppointmentService {
    void createAppointment(Doctor doctor, Patient patient, AppointmentSlot appointmentSlot);
}
