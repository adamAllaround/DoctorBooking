package com.allaroundjava.controller;

import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.DoctorService;
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

public class DoctorControllerTest {
    private DoctorController doctorController;
    private DoctorService doctorService;
    private MockMvc mockMvc;

    public DoctorControllerTest() {
        doctorService = Mockito.mock(DoctorService.class);
        doctorController = new DoctorController(doctorService);
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    public void whenDoctorNotExists_thenHttp400() throws Exception {
        Mockito.doReturn(Optional.empty()).when(doctorService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void whenDoctorExists_thenHttp200_andDoctorReturned() throws Exception {
        String doctorName = "Doctor";
        Doctor testDoctor = new Doctor(doctorName);
        Mockito.doReturn(Optional.of(testDoctor)).when(doctorService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
        .andExpect(MockMvcResultMatchers.content().string(containsString(doctorName)));
    }

    @Test
    public void whenPostDoctor_thenAddDoctorCalled_andDoctorReturned() throws Exception {
        String newDoctor = "<DoctorDto><name>Doctor John</name></DoctorDto>";
        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                .content(newDoctor)
                .header("Content-Type","application/xml"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.xpath("/DoctorDto/name", "%s")
                        .string(containsString("Doctor John")));
    }
}