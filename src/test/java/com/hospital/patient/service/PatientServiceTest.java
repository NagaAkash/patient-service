package com.hospital.patient.service;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientService service;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john.doe@example.com");
        patient.setPhone("1234567890");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setAddress("123 Main St");
    }

    @Test
    void testSave() {
        when(repository.save(any(Patient.class))).thenReturn(patient);

        Patient saved = service.save(patient);

        assertNotNull(saved);
        assertEquals("John", saved.getFirstName());
        verify(repository, times(1)).save(patient);
    }

    @Test
    void testFindById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient found = service.findById(1L);

        assertNotNull(found);
        assertEquals("john.doe@example.com", found.getEmail());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.findById(999L);
        });

        assertEquals("Patient not found", exception.getMessage());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
        List<Patient> patients = Arrays.asList(patient);
        when(repository.findAll()).thenReturn(patients);

        List<Patient> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getLastName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testUpdate_Success() {
        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Doe");
        updatedPatient.setEmail("jane.doe@example.com");

        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        when(repository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = service.update(1L, updatedPatient);

        assertEquals("Jane", result.getFirstName());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Patient.class));
    }
    @Test
    void testFindAll_Empty() {
        when(repository.findAll()).thenReturn(Arrays.asList());
        List<Patient> result = service.findAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}
