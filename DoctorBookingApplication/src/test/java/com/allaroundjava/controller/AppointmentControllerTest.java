package com.allaroundjava.controller;

import com.allaroundjava.model.Appointment;
import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Patient;
import com.allaroundjava.service.AppointmentService;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

public class AppointmentControllerTest {
    private AppointmentController appointmentController;
    private AppointmentService appointmentService;
    private AppointmentSlotService appointmentSlotService;
    private PatientService patientService;
    private MockMvc mockMvc;
    private final static String REQUEST_BODY = "<AppointmentDto><patientId>1</patientId><appointmentSlotId>1</appointmentSlotId><startTime>2019-05-13T20:00:00</startTime><endTime>2019-05-13T21:00:00</endTime></AppointmentDto>";

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
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                .contentType(MediaType.APPLICATION_XML)
                .content(REQUEST_BODY))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenBookingAppointment_andAppointmentSlotNotExists_thenNotFound() throws Exception {
        Patient patient = new Patient("Patient John");
        Mockito.doReturn(Optional.of(patient)).when(patientService).getById(1L);
        Mockito.doReturn(Optional.empty()).when(appointmentSlotService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                .contentType(MediaType.APPLICATION_XML)
                .content(REQUEST_BODY))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenBookingAppointment_andPatientAndSlotExist_thenOk() throws Exception {
        Patient patient = new Patient("Patient John");
        Mockito.doReturn(Optional.of(patient)).when(patientService).getById(1L);
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setStartTime(LocalDateTime.of(2019, 5, 21, 10, 0, 0));
        appointmentSlot.setEndTime(LocalDateTime.of(2019, 5, 21, 10, 30, 0));
        Mockito.doReturn(Optional.of(appointmentSlot)).when(appointmentSlotService).getById(1L);
        Appointment appointment = new Appointment(appointmentSlot, patient);
        Mockito.doReturn(appointment).when(appointmentService).createAppointment(patient, appointmentSlot);
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                .contentType(MediaType.APPLICATION_XML)
                .content(REQUEST_BODY))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }
}