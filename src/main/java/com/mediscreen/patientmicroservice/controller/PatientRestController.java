package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PatientRestController {

    private final PatientService patientService;

    @Autowired
    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping("/rest/patient")
    public List<Patient> listAllPatients(){
        return patientService.findAll();
    }

    @GetMapping("/rest/patient/{id}")
    public Optional<Patient> getPatientById(@PathVariable Integer id){
        return patientService.findById(id);
    }

    @PutMapping("/rest/patient/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @RequestBody @Valid Patient patientDetails){
        Patient patient = patientService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id :"+ id));

        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setAddress(patientDetails.getAddress());
        patient.setFamily(patientDetails.getFamily());
        patient.setPhone(patientDetails.getPhone());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setSex(patientDetails.getSex());

        patientService.save(patient);

        return  ResponseEntity.ok(patient);
    }

    @DeleteMapping("/rest/patient/delete/{id}")
    public void deletePatient(@PathVariable Integer id){
        patientService.deleteById(id);
    }

    @PostMapping("/rest/patient/add")
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient){
        Patient patientSaved = patientService.save(patient);

        if(patientSaved == null)
            return ResponseEntity.noContent().build();

    URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/patient/{id}")
            .buildAndExpand(patientSaved.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

}
