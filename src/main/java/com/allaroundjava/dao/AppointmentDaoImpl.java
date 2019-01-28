package com.allaroundjava.dao;

import com.allaroundjava.model.Appointment;

import javax.persistence.EntityManagerFactory;

public class AppointmentDaoImpl extends BaseDao<Appointment> implements AppointmentDao {
    public AppointmentDaoImpl(EntityManagerFactory emf) {
        super(Appointment.class, emf);
    }
}
