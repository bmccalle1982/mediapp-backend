package com.mitocode.repository.impl;

import com.mitocode.model.Patient;
import org.springframework.stereotype.Repository;

@Repository
public class PatientRepositoryImpl
{
    public Patient getPatientInfo(Integer id)
    {
        return new Patient();
    }
}
