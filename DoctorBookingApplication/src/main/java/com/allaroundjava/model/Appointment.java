package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Immutable
public class Appointment extends ModelBase{
    @OneToOne(optional = false)
    private AppointmentSlot appointmentSlot;
    @ManyToOne(optional = false)
    private Patient patient;

    Appointment() {
    }

    public Appointment(AppointmentSlot appointmentSlot, Patient patient) {
        this.appointmentSlot = appointmentSlot;
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AppointmentSlot getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(AppointmentSlot appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }
}
