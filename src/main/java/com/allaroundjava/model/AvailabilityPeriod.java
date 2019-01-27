package com.allaroundjava.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class AvailabilityPeriod extends ModelBase {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "doctor_id")
    private Doctor doctor;

    AvailabilityPeriod() {
    }

    public AvailabilityPeriod(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
