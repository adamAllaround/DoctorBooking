package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Period;

@Entity
@Immutable
public class Appointment extends ModelBase{
    @ManyToOne(optional = false)
    private Doctor doctor;
    @ManyToOne(optional = false)
    private Patient patient;
    private Period period;

    Appointment() {
    }

    public Appointment(Doctor doctor, Patient patient, Period period) {
        this.doctor = doctor;
        this.patient = patient;
        this.period = period;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Period getPeriod() {
        return period;
    }
}
