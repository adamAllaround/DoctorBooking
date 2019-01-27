package com.allaroundjava.dao;

import com.allaroundjava.model.Appointment;

import javax.persistence.EntityManagerFactory;

public class AppointmentDaoImpl extends BaseDao<Appointment> {
    AppointmentDaoImpl(EntityManagerFactory emf) {
        super(Appointment.class, emf);
    }
}
