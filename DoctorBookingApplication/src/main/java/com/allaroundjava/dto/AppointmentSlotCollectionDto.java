package com.allaroundjava.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentSlotCollectionDto {
    @XmlElement(name = "AppointmentSlotCollection")
    private List<AppointmentSlotDto> appointmentSlotDtoList;

    public List<AppointmentSlotDto> getAppointmentSlotDtoList() {
        return appointmentSlotDtoList;
    }

    public void setAppointmentSlotDtoList(List<AppointmentSlotDto> appointmentSlotDtoList) {
        this.appointmentSlotDtoList = appointmentSlotDtoList;
    }
}
