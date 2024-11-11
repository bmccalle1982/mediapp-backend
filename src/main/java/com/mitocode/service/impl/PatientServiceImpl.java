package com.mitocode.service.impl;

import com.mitocode.model.Patient;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.PatientRepository;
import com.mitocode.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDServiceImpl<Patient, Integer> implements PatientService
{
    private final PatientRepository patientRepository;

    @Override
    protected GenericRepository<Patient, Integer> getRepository() {
        return patientRepository;
    }
}
