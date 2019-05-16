package com.allaroundjava.controller;

import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import com.allaroundjava.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

public class AppointmentControllerTest {
    private AppointmentController appointmentController;
    private AppointmentService appointmentService;
    private AppointmentSlotService appointmentSlotService;
    private PatientService patientService;
    private MockMvc mockMvc;

    public AppointmentControllerTest() {
        this.appointmentService = Mockito.mock(AppointmentService.class);
        this.appointmentSlotService = Mockito.mock(AppointmentSlotService.class);
        this.patientService = Mockito.mock(PatientService.class);
        this.appointmentController = new AppointmentController(appointmentService, appointmentSlotService, patientService);
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    public void whenBookingAppointment_andPatientNotExists_thenNotFound() throws Exception {
        Mockito.doReturn(Optional.empty()).when(patientService).getById(1L);
        String requestBody="<appointmentDto><doctorId>1</doctorId><patientId>1</patientId><startTime>2019-05-13T20:00:00</startTime><endTime>2019-05-13T21:00:00</endTime></appointmentDto>";
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                .contentType(MediaType.APPLICATION_XML)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}