package com.allaroundjava.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentDto {
    private Long entityId;
    private Long patientId;
    private Long appointmentSlotId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getAppointmentSlotId() {
        return appointmentSlotId;
    }

    public void setAppointmentSlotId(Long appointmentSlotId) {
        this.appointmentSlotId = appointmentSlotId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

}
