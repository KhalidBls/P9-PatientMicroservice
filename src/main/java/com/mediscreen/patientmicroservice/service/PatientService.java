package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> findById(Integer id) {
        return patientRepository.findById(id);
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public void addOrUpdatePatient(Patient patientToSave) {

        Patient patient = null;

        if(patientToSave.getId() != null) {
            patient = findById(patientToSave.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid patient id :"+ patientToSave.getId()));
            patient.setFirstName(patientToSave.getFirstName());
            patient.setLastName(patientToSave.getLastName());
            patient.setFamily(patientToSave.getFamily());
            patient.setDateOfBirth(patientToSave.getDateOfBirth());
            patient.setAddress(patientToSave.getAddress());
            patient.setPhone(patientToSave.getPhone());
            patient.setSex(patientToSave.getSex());
            save(patient);
        }else{
            save(patientToSave);
        }
    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }
}