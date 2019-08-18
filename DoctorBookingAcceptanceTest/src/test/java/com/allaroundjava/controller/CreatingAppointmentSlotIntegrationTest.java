package com.allaroundjava.controller;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.IntegrationTestConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.dto.AppointmentSlotDto;
import com.allaroundjava.dto.DoctorDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = {TestJpaConfig.class, AppConfig.class, IntegrationTestConfig.class})
public class CreatingAppointmentSlotIntegrationTest {
    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void whenCreatingSlotForExistingDoctor_thenSlotIsCreated() {
        DoctorDto doctor = createDoctorDto("Doctor John");
        String doctorId =
            given()
                .contentType(ContentType.XML)
                .body(doctor)
            .when()
                .post("/doctors")
            .then()
                .log().all()
                .statusCode(201)
                .body("doctorDto.entityId", notNullValue())
                .extract().path("doctorDto.entityId");

        AppointmentSlotDto appointmentSlotDto = createAppointmentSlotDto(doctorId);

        given()
            .contentType(ContentType.XML)
            .body(appointmentSlotDto)
        .when()
            .post("/slots")
        .then()
            .log().all()
            .statusCode(201)
            .body("appointmentSlotDto.doctorId", equalTo(doctorId))
            .body("appointmentSlotDto.entityId", notNullValue());
    }

    private DoctorDto createDoctorDto(String name) {
        DoctorDto doctor = new DoctorDto();
        doctor.setName(name);
        return doctor;
    }

    private AppointmentSlotDto createAppointmentSlotDto(String doctorId) {
        AppointmentSlotDto appointmentSlotDto = new AppointmentSlotDto();
        appointmentSlotDto.setDoctorId(Long.valueOf(doctorId));
        LocalDateTime startTime = LocalDateTime.of(2019, 8, 18, 19, 0);
        LocalDateTime endTime = LocalDateTime.of(2019, 8, 18, 20, 0);
        appointmentSlotDto.setStartTime(startTime);
        appointmentSlotDto.setEndTime(endTime);
        return appointmentSlotDto;
    }
}
