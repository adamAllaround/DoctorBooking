package com.allaroundjava.service;

import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.model.Patient;

public interface AppointmentService {
    Appointment createAppointment(Patient patient, AppointmentSlot appointmentSlot);
}
