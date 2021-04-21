package com.mediscreen.patientmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientRestController.class)
@RunWith(SpringRunner.class)
public class PatientRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Test
    public void testCreatePatient() throws Exception {
        //Given
        Patient patient;
        ObjectMapper mapper = new ObjectMapper();
        String PatientJSON = "{\n" +
                "        \"firstName\": \"jack\",\n" +
                "        \"lastName\": \"jacky\",\n" +
                "        \"sex\": \"M\",\n" +
                "        \"address\": \"address of jackies\",\n" +
                "        \"dateOfBirth\": \"01/10/1997\",\n" +
                "        \"phone\": \"0123456789\"\n" +
                "    }";
        patient = mapper.readValue(PatientJSON, Patient.class);

        //When
        when(patientService.save(any(Patient.class))).thenReturn(patient);

        //Then
        mockMvc.perform(post("/rest/patient/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PatientJSON))
                .andExpect(status().is(201));

        assertTrue(patient.getFirstName().equals("jack"));
    }

    @Test
    public void testGetPatientById() throws Exception {

        //ARRANGE
        Patient patient = new Patient("bob", "bobby", "M",
                "address of bobbies", "0123456789", "01/10/1997");
        patient.setId(77);

        //ACT
        when(patientService.findById(77)).thenReturn(java.util.Optional.of(patient));

        //ASSERT
        mockMvc.perform(get("/rest/patient/77")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(patient.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(patient.getLastName())))
                .andExpect(jsonPath("$.address", is(patient.getAddress())))
                .andExpect(jsonPath("$.phone", is(patient.getPhone())))
                .andExpect(jsonPath("$.sex", is(patient.getSex())));

        verify(patientService,times(1)).findById(patient.getId());
    }

    @Test
    public void testUpdatePatient() throws Exception {
        //GIVEN
        Patient patient = new Patient("bob", "bobby", "M",
                "address of bobbies", "0123456789", "01/10/1997");
        patient.setId(77);

        String patientJSON = "{\n" +
                "        \"firstName\": \"jack\",\n" +
                "        \"lastName\": \"jacky\",\n" +
                "        \"sex\": \"M\",\n" +
                "        \"address\": \"address of jackies\",\n" +
                "        \"dateOfBirth\": \"01/10/1997\",\n" +
                "        \"phone\": \"0123456789\"\n" +
                "    }";

        //WHEN
        when(patientService.findById(77)).thenReturn(java.util.Optional.of(patient));
        when(patientService.save(patient)).thenReturn(patient);

        //THEN
        mockMvc.perform(put("/rest/patient/update/77")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientJSON))
                .andExpect(status().is(200));

        verify(patientService,times(1)).findById(patient.getId());
        verify(patientService,times(1)).save(patient);
        assertTrue(patient.getFirstName().equals("jack"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //GIVEN
        Patient patient = new Patient("bob", "bobby", "M",
                "address of bobbies", "0123456789", "01/10/1997");
        patient.setId(77);

        //WHEN
        doNothing().when(patientService).deleteById(77);
        //THEN
        mockMvc.perform(delete("/rest/patient/delete/77")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(patientService,times(1)).deleteById(patient.getId());
    }

}
