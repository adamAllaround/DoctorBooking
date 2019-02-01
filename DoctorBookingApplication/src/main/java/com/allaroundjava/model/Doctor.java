package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Immutable
public class Doctor extends ModelBase {
    private String name;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    Doctor() {
    }

    public Doctor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }
}
