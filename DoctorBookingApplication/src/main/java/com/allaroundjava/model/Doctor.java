package com.allaroundjava.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;

@Entity
@Immutable
public class Doctor extends ModelBase {
    private String name;
    Doctor() {
    }

    public Doctor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
