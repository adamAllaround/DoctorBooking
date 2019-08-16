package com.allaroundjava.controller;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.IntegrationTestConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.dto.AppointmentDto;
import com.allaroundjava.dto.AppointmentSlotCollectionDto;
import com.allaroundjava.dto.AppointmentSlotDto;
import com.allaroundjava.dto.DoctorDto;
import com.allaroundjava.dto.PatientDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TestJpaConfig.class, AppConfig.class, IntegrationTestConfig.class})
public class BookingAppointmentIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void havingDoctorPatientAndAppointmentSlot_thenAppointmentSlotCanBeBooked() {
        HttpEntity<DoctorDto> doctorHttpEntity = getDoctorDtoHttpEntity("Doctor John");
        HttpEntity<PatientDto> patientDtoHttpEntity = getPatientDtoHttpEntity("Patient Mary");

        ResponseEntity<DoctorDto> doctorExchange = restTemplate.exchange("/doctors", HttpMethod.POST, doctorHttpEntity, DoctorDto.class);
        Assert.assertTrue(doctorExchange.getStatusCode().is2xxSuccessful());

        ResponseEntity<PatientDto> patientExchange = restTemplate.exchange("/patients", HttpMethod.POST, patientDtoHttpEntity, PatientDto.class);
        Assert.assertTrue(patientExchange.getStatusCode().is2xxSuccessful());

        LocalDateTime appointmentStart = LocalDateTime.of(2019, 6, 3, 10, 0, 0);
        LocalDateTime appointmentEnd = LocalDateTime.of(2019, 6, 3, 11, 0, 0);

        HttpEntity<AppointmentSlotDto> appointmentSlotDtoHttpEntity = getAppointmentSlotDtoHttpEntity(doctorExchange.getBody().getEntityId(), appointmentStart, appointmentEnd);
        ResponseEntity<AppointmentSlotDto> slotExchange = restTemplate.exchange("/slots", HttpMethod.POST, appointmentSlotDtoHttpEntity, AppointmentSlotDto.class);
        Assert.assertTrue(slotExchange.getStatusCode().is2xxSuccessful());


        String slotsUrl = String.format("/slots?doctorId=%d&startDate=%s&endDate=%s",
                doctorExchange.getBody().getEntityId(),
                appointmentStart.format(DateTimeFormatter.ISO_DATE_TIME),
                appointmentEnd.plusHours(2).format(DateTimeFormatter.ISO_DATE_TIME));
        ResponseEntity<AppointmentSlotCollectionDto> availableSlots = restTemplate.getForEntity(slotsUrl, AppointmentSlotCollectionDto.class);
        Assert.assertTrue(availableSlots.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(1, availableSlots.getBody().getAppointmentSlotDtoList().size());

        HttpEntity<AppointmentDto> appointmentDtoHttpEntity = getAppointmentDtoHttpEntity(slotExchange.getBody().getEntityId(), patientExchange.getBody().getEntityId());
        ResponseEntity<AppointmentDto> appointmentExchange = restTemplate.exchange("/appointments", HttpMethod.POST, appointmentDtoHttpEntity, AppointmentDto.class);
        Assert.assertTrue(appointmentExchange.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void whenAppointmentSlotIsBooked_thenItCannotBeBookedAgain() {
        HttpEntity<DoctorDto> doctorHttpEntity = getDoctorDtoHttpEntity("Doctor Fred");
        HttpEntity<PatientDto> patientDtoHttpEntity = getPatientDtoHttpEntity("Patient Anne");

        ResponseEntity<DoctorDto> doctorExchange = restTemplate.exchange("/doctors", HttpMethod.POST, doctorHttpEntity, DoctorDto.class);
        Assert.assertTrue(doctorExchange.getStatusCode().is2xxSuccessful());

        ResponseEntity<PatientDto> patientExchange = restTemplate.exchange("/patients", HttpMethod.POST, patientDtoHttpEntity, PatientDto.class);
        Assert.assertTrue(patientExchange.getStatusCode().is2xxSuccessful());

        LocalDateTime appointmentStart = LocalDateTime.of(2019, 6, 4, 10, 0, 0);
        LocalDateTime appointmentEnd = LocalDateTime.of(2019, 6, 4, 11, 0, 0);

        HttpEntity<AppointmentSlotDto> appointmentSlotDtoHttpEntity = getAppointmentSlotDtoHttpEntity(doctorExchange.getBody().getEntityId(), appointmentStart, appointmentEnd);
        ResponseEntity<AppointmentSlotDto> slotExchange = restTemplate.exchange("/slots", HttpMethod.POST, appointmentSlotDtoHttpEntity, AppointmentSlotDto.class);
        Assert.assertTrue(slotExchange.getStatusCode().is2xxSuccessful());

        String slotsUrl = String.format("/slots?doctorId=%d&startDate=%s&endDate=%s",
                doctorExchange.getBody().getEntityId(),
                appointmentStart.minusHours(1).format(DateTimeFormatter.ISO_DATE_TIME),
                appointmentEnd.plusHours(1).format(DateTimeFormatter.ISO_DATE_TIME));
        ResponseEntity<AppointmentSlotCollectionDto> availableSlots = restTemplate.getForEntity(slotsUrl, AppointmentSlotCollectionDto.class);
        Assert.assertTrue(availableSlots.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(1, availableSlots.getBody().getAppointmentSlotDtoList().size());

        HttpEntity<AppointmentDto> appointmentDtoHttpEntity = getAppointmentDtoHttpEntity(slotExchange.getBody().getEntityId(), patientExchange.getBody().getEntityId());
        ResponseEntity<AppointmentDto> appointmentExchange = restTemplate.exchange("/appointments", HttpMethod.POST, appointmentDtoHttpEntity, AppointmentDto.class);
        Assert.assertTrue(appointmentExchange.getStatusCode().is2xxSuccessful());

        appointmentDtoHttpEntity = getAppointmentDtoHttpEntity(slotExchange.getBody().getEntityId(), patientExchange.getBody().getEntityId());
        appointmentExchange = restTemplate.exchange("/appointments", HttpMethod.POST, appointmentDtoHttpEntity, AppointmentDto.class);
        Assert.assertTrue(appointmentExchange.getStatusCode().is4xxClientError());
    }

    private HttpEntity<PatientDto> getPatientDtoHttpEntity(String name) {
        HttpHeaders httpHeaders = getHttpHeaders();
        PatientDto patientDto = new PatientDto();
        patientDto.setName(name);
        return new HttpEntity<>(patientDto, httpHeaders);
    }

    private HttpEntity<DoctorDto> getDoctorDtoHttpEntity(String name) {
        HttpHeaders headers = getHttpHeaders();
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName(name);
        return new HttpEntity<>(doctorDto, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return headers;
    }

    private HttpEntity<AppointmentSlotDto> getAppointmentSlotDtoHttpEntity(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        HttpHeaders httpHeaders = getHttpHeaders();
        AppointmentSlotDto appointmentSlotDto = new AppointmentSlotDto();
        appointmentSlotDto.setDoctorId(doctorId);
        appointmentSlotDto.setStartTime(startTime);
        appointmentSlotDto.setEndTime(endTime);
        return new HttpEntity<>(appointmentSlotDto, httpHeaders);
    }

    private HttpEntity<AppointmentDto> getAppointmentDtoHttpEntity(Long slotId, Long patientId) {
        HttpHeaders httpHeaders = getHttpHeaders();
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentSlotId(slotId);
        appointmentDto.setPatientId(patientId);
        return new HttpEntity<>(appointmentDto, httpHeaders);
    }
}
