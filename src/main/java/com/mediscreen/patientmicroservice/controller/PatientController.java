package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patientForm")
    public ModelAndView showHome(@RequestParam(required = false) Integer id) {

        String viewName = "patientForm";

        Map<String,Object> model = new HashMap<>();

        if(id != null) {
            Optional<Patient> patient = patientService.findById(id);

            if(!patient.isPresent())
                model.put("patient", new Patient());
            else
                model.put("patient", patient.get());
        }else
            model.put("patient", new Patient());

        return new ModelAndView(viewName,model);
    }

    @PostMapping("/patientForm")
    public ModelAndView submitPatient(@Valid Patient patient, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ModelAndView("patientForm");
        }

        patientService.addOrUpdatePatient(patient);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/patients");
        return new ModelAndView(redirect);
    }

    @GetMapping("/patients")
    public ModelAndView getPatients() {

        String viewName = "list";

        Map<String,Object> model = new HashMap<>();

        model.put("patients", patientService.findAll());

        return new ModelAndView(viewName,model);
    }

    @GetMapping("/patient/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Integer id) {
        patientService.deleteById(id);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/patients");
        return new ModelAndView(redirect);
    }

}
