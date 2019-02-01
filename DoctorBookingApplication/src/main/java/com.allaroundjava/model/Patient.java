package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Immutable
public class Patient extends ModelBase {
    private String name;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    Patient() {
    }

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }
}
