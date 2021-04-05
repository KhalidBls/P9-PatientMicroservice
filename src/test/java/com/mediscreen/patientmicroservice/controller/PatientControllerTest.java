package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RunWith(SpringRunner.class)
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Test
    public void testShowPatientForm() throws Exception {

        mockMvc.perform(get("/patientForm"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("patientForm"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void testSubmitPatientForm() throws Exception {
        mockMvc.perform(post("/patientForm")
                .param("firstName", "bob")
                .param("lastName", "bobby")
                .param("sex", "M")
                .param("address", "Rue des Bobby")
                .param("dateOfBirth", "01/10/1997")
                .param("phone", "0123456789"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    public void testGetPatients() throws Exception {

        mockMvc.perform(get("/patients"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("list"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("patients"));

    }

}
