package com.hospital.patient.controller;

import com.hospital.patient.entity.Patient;
import com.hospital.patient.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@Valid @RequestBody Patient patient) {
        logger.info("Creating patient with email: {}", patient.getEmail());
        logger.debug("Patient details: firstName={}, lastName={}", patient.getFirstName(), patient.getLastName());
        return service.save(patient);
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable Long id) {
        logger.info("Fetching patient with ID: {}", id);
        return service.findById(id);
    }

    @GetMapping
    public List<Patient> getAll() {
        logger.info("Fetching all patients");
        return service.findAll();
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        logger.info("Updating patient with ID: {}", id);
        return service.update(id, patient);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        logger.info("Deleting patient with ID: {}", id);
        service.delete(id);
    }
    @GetMapping("/by-name/{firstName}")
    public Patient getPatientByFirstName(@PathVariable("firstName") String firstName) {
        return service.getPatientByFirstName(firstName);
    }
}