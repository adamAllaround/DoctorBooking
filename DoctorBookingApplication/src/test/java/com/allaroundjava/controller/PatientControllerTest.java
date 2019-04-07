package com.allaroundjava.controller;

import com.allaroundjava.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

public class PatientControllerTest {
    private PatientController patientController;
    private PatientService patientService;
    private MockMvc mockMvc;

    public PatientControllerTest() {
        patientService = Mockito.mock(PatientService.class);
        patientController = new PatientController(patientService);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    public void whenPatientNotExists_thenHttp400() throws Exception {
        Mockito.doReturn(Optional.empty()).when(patientService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }
}