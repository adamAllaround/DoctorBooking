package com.allaroundjava.controller;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.IntegrationTestConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.dto.DoctorDto;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class, AppConfig.class, IntegrationTestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenCreateDoctor_thenDoctorCanBeRetrieved() {
        HttpEntity<DoctorDto> requestEntity = getDoctorDtoHttpEntity("Doctor John");

        ResponseEntity<DoctorDto> exchange = restTemplate.exchange("/doctors", HttpMethod.POST, requestEntity, DoctorDto.class);
        Assert.assertTrue(exchange.getStatusCode().is2xxSuccessful());

        ResponseEntity<DoctorDto> forEntity = restTemplate.getForEntity("/doctors/"+exchange.getBody().getEntityId(), DoctorDto.class);
        Assert.assertTrue(forEntity.getStatusCode().is2xxSuccessful());

        ResponseEntity<DoctorDto> nonExistentDoctor = restTemplate.getForEntity("/doctors/"+12478, DoctorDto.class);
        Assert.assertTrue(nonExistentDoctor.getStatusCode().is4xxClientError());
    }

    private HttpEntity<DoctorDto> getDoctorDtoHttpEntity(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName(name);
        return new HttpEntity<>(doctorDto, headers);
    }
}
