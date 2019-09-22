package com.allaroundjava.controller;

import com.allaroundjava.model.AppointmentSlot;
import com.allaroundjava.model.Doctor;
import com.allaroundjava.service.AppointmentSlotService;
import com.allaroundjava.service.DoctorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class AppointmentSlotControllerTest {
    private AppointmentSlotController appointmentSlotController;
    private AppointmentSlotService appointmentSlotService;
    private DoctorService doctorService;
    private MockMvc mockMvc;
    private Doctor doctor;

    public AppointmentSlotControllerTest() {
        appointmentSlotService = Mockito.mock(AppointmentSlotService.class);
        doctorService = Mockito.mock(DoctorService.class);
        appointmentSlotController = new AppointmentSlotController(appointmentSlotService, doctorService);
        doctor = new Doctor("Doctor John");
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentSlotController).build();
    }

    @Test
    public void whenValidRequest_andDoctorExists_thenCreated() throws Exception {
        String requestContent = "<AppointmentSlotDto><startTime>2019-05-14T20:00:22</startTime><endTime>2019-05-14T21:00:22</endTime><doctorId>1</doctorId></AppointmentSlotDto>";
        Mockito.doReturn(Optional.of(doctor)).when(doctorService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/slots")
                .content(requestContent)
                .header("Content-Type","application/xml"))
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

        Mockito.verify(appointmentSlotService, times(1)).addAppointmentSlot(any());
    }

    @Test
    public void whenValidRequest_andDoctorNotExists_thenNotFound() throws Exception {
        String requestContent = "<AppointmentSlotDto><startTime>2019-05-14T20:00:22</startTime><endTime>2019-05-14T21:00:22</endTime><doctorId>1</doctorId></AppointmentSlotDto>";
        Mockito.doReturn(Optional.empty()).when(doctorService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/slots")
                .content(requestContent)
                .header("Content-Type","application/xml"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));

        Mockito.verify(appointmentSlotService, never()).addAppointmentSlot(any());
    }

    @Test
    public void whenGetForNonExistentId_thenNotFound() throws Exception {
        Mockito.doReturn(Optional.empty()).when(appointmentSlotService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/slots/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void whenGetForExistingId_thenOk() throws Exception {
        LocalDateTime slotStart = LocalDateTime.of(2019, 5, 13, 10, 0, 0);
        LocalDateTime slotEnd = LocalDateTime.of(2019, 5, 13, 11, 0, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(slotStart, slotEnd, doctor);

        Mockito.doReturn(Optional.of(appointmentSlot)).when(appointmentSlotService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/slots/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
        .andExpect(MockMvcResultMatchers.content().string(containsString("startTime")));
    }

    @Test
    public void whenGetForSlots_andDoctorNotExists_thenNotFound() throws Exception {
        LocalDateTime slotStart = LocalDateTime.of(2019, 5, 13, 10, 0, 0);
        LocalDateTime slotEnd = LocalDateTime.of(2019, 5, 13, 11, 0, 0);

        Mockito.doReturn(Optional.empty()).when(doctorService).getById(1L);

        String path = String.format("/slots?doctorId=1&startDate=%s&endDate=%s", slotStart.format(DateTimeFormatter.ISO_DATE_TIME), slotEnd.format(DateTimeFormatter.ISO_DATE_TIME));
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void whenGetForSlots_andDoctorExists_thenOk() throws Exception {
        LocalDateTime slotStart = LocalDateTime.of(2019, 5, 13, 10, 0, 0);
        LocalDateTime slotEnd = LocalDateTime.of(2019, 5, 13, 11, 0, 0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(slotStart, slotEnd, doctor);

        Mockito.doReturn(Optional.of(doctor)).when(doctorService).getById(1L);
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        appointmentSlots.add(appointmentSlot);

        Mockito.doReturn(appointmentSlots).when(appointmentSlotService).getAppointmentSlotsBetween(doctor, slotStart, slotEnd);

        String path = String.format("/slots?doctorId=1&startDate=%s&endDate=%s", slotStart.format(DateTimeFormatter.ISO_DATE_TIME), slotEnd.format(DateTimeFormatter.ISO_DATE_TIME));
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(containsString("AppointmentSlotCollection")));
    }
}