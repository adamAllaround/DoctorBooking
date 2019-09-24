package com.allaroundjava.controller;

import com.allaroundjava.controller.advice.RestExceptionHandler;
import com.allaroundjava.model.Patient;
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

import static org.hamcrest.CoreMatchers.containsString;

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
        mockMvc = MockMvcBuilders.standaloneSetup(patientController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void whenPatientNotExists_thenHttp400() throws Exception {
        Mockito.doReturn(Optional.empty()).when(patientService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void whenPatientExists_then200() throws Exception {
        String patientName = "Patient John";
        Patient patient = new Patient(patientName);
        Mockito.doReturn(Optional.of(patient)).when(patientService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
        .andExpect(MockMvcResultMatchers.content().string(containsString(patientName)));
    }

    @Test
    public void whenPostDoctor_thenAddDoctorCalled_andDoctorReturned() throws Exception {
        String newPatient = "<PatientDto><name>Patient John</name></PatientDto>";
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                .content(newPatient)
                .header("Content-Type","application/xml"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Patient John")));
    }
}