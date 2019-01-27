package com.allaroundjava.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Period;

@Entity
public class AvailabilityPeriod extends ModelBase {
    private Period period;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "doctor_id")
    private Doctor doctor;

    AvailabilityPeriod() {
    }

    public AvailabilityPeriod(Period period, Doctor doctor) {
        this.period = period;
        this.doctor = doctor;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
