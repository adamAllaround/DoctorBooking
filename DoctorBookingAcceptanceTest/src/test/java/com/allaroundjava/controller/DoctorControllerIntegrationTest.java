package com.allaroundjava.controller;

import com.allaroundjava.config.AppConfig;
import com.allaroundjava.config.TestJpaConfig;
import com.allaroundjava.dto.DoctorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class, AppConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();


    @Test
    public void testSomeService() {
        testRestTemplate.getForEntity(prepaLocalUriWithLocation("/doctors/1"), DoctorDto.class)
                .getStatusCode()
                .is4xxClientError();

    }

    private String prepaLocalUriWithLocation(String location) {
        return String.format("http://localhost:%d/", port);
    }
}
