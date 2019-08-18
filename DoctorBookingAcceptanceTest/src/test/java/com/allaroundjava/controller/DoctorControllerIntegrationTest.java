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
import org.springframework.http.*;
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
        //Given DoctorDto object
        HttpEntity<DoctorDto> requestEntity = getDoctorDtoHttpEntity("Doctor John");

        //When Post created DoctorDto
        ResponseEntity<DoctorDto> postDoctor = restTemplate.exchange("/doctors", HttpMethod.POST, requestEntity, DoctorDto.class);
        Assert.assertEquals(postDoctor.getStatusCode(), HttpStatus.CREATED);
        Assert.assertNotNull(postDoctor.getBody().getEntityId());

        //Then Doctor can be retrieved
        ResponseEntity<DoctorDto> getDoctor = restTemplate.getForEntity("/doctors/"+postDoctor.getBody().getEntityId(), DoctorDto.class);
        Assert.assertTrue(getDoctor.getStatusCode().is2xxSuccessful());

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
