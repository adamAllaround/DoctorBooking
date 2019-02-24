package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;

@Entity
@Immutable
public class Patient extends ModelBase {
    private String name;

    Patient() {
    }

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
