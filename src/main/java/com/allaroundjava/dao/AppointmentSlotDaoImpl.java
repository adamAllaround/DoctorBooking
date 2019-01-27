package com.allaroundjava.dao;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentSlotDaoImpl extends BaseDao<AppointmentSlot> implements AppointmentSlotDao {

    AppointmentSlotDaoImpl(EntityManagerFactory emf) {
        super(AppointmentSlot.class, emf);
    }

    @Override
    public List<AppointmentSlot> getAppointmentSlotsBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createNamedQuery("appointmentSlotsBetween", AppointmentSlot.class);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }
}
