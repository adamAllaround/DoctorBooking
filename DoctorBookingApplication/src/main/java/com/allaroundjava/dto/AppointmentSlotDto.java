package com.allaroundjava.dto;

import com.allaroundjava.adapter.LocalDateTimeAdapter;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentSlotDto extends ResourceSupport {
    private Long entityId;
    private Long doctorId;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
