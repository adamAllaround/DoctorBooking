package com.allaroundjava.dto;

import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DoctorDto extends ResourceSupport {
    private Long entityId;
    private String name;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
