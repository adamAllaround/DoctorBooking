package com.allaroundjava.dao;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AppointmentSlotDaoImpl extends BaseDao<AppointmentSlot> implements AppointmentSlotDao {

    public AppointmentSlotDaoImpl() {
        super(AppointmentSlot.class);
    }

    @Override
    public List<AppointmentSlot> getAppointmentSlotsBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        Query query = entityManager.createNamedQuery("appointmentSlotsBetween", AppointmentSlot.class);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }
}
