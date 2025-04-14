package com.hospital.patient.service;

import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository repository;

    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    public Patient findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public Patient update(Long id, Patient patient) {
        Patient existing = findById(id);
        existing.setFirstName(patient.getFirstName());
        existing.setLastName(patient.getLastName());
        existing.setEmail(patient.getEmail());
        existing.setPhone(patient.getPhone());
        existing.setDateOfBirth(patient.getDateOfBirth());
        existing.setAddress(patient.getAddress());
        return repository.save(existing);
    }
    public void delete(Long id) {
        Patient existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        repository.delete(existing);
    }


}