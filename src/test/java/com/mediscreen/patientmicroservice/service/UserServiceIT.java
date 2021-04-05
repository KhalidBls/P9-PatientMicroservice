package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    @Autowired
    PatientService patientService;

    @Test
    public void patientTest() {

        for (Patient patient : patientService.findAll()){
            patientService.deleteById(patient.getId());
        }

        Patient patient = new Patient();
        patient.setFirstName("bob");
        patient.setLastName("bobby");
        patient.setSex("M");
        patient.setPhone("0123456789");
        patient.setAddress("Rue des Bobby");
        patient.setDateOfBirth("01/10/1997");

        // Save
        patientService.addOrUpdatePatient(patient);
        assertNotNull(patient.getId());
        assertEquals("bob", patientService.findById(patient.getId()).get().getFirstName());
        assertEquals("bobby", patientService.findById(patient.getId()).get().getLastName());
        assertEquals("M", patientService.findById(patient.getId()).get().getSex());
        assertEquals("0123456789", patientService.findById(patient.getId()).get().getPhone());
        assertEquals("Rue des Bobby", patientService.findById(patient.getId()).get().getAddress());
        assertEquals("01/10/1997", patientService.findById(patient.getId()).get().getDateOfBirth());

        // Update
        patient.setFirstName("Jack update");
        patientService.addOrUpdatePatient(patient);
        assertEquals("Jack update", patientService.findById(patient.getId()).get().getFirstName());
        assertEquals("bobby", patientService.findById(patient.getId()).get().getLastName());
        assertEquals("M", patientService.findById(patient.getId()).get().getSex());
        assertEquals("0123456789", patientService.findById(patient.getId()).get().getPhone());
        assertEquals("Rue des Bobby", patientService.findById(patient.getId()).get().getAddress());
        assertEquals("01/10/1997", patientService.findById(patient.getId()).get().getDateOfBirth());

        // Find
        List<Patient> listResult = patientService.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = patient.getId();
        patientService.deleteById(id);
        Optional<Patient> patient1 = patientService.findById(id);
        Assert.assertFalse(patient1.isPresent());
    }

}
