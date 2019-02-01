package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Immutable
public class Appointment extends ModelBase{
    @ManyToOne(optional = false)
    private Doctor doctor;
    @ManyToOne(optional = false)
    private Patient patient;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    Appointment() {
    }

    public Appointment(Doctor doctor, Patient patient, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
