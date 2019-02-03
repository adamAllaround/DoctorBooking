package com.allaroundjava.dao;

import com.allaroundjava.model.Appointment;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentDaoImpl extends BaseDao<Appointment> implements AppointmentDao {
    public AppointmentDaoImpl() {
        super(Appointment.class);
    }
}
