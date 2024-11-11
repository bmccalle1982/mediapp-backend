package com.mitocode.repository;

import com.mitocode.model.Patient;

//No es necesario agregar la anotación @Repository,Spring asume que debe agregar una interface en automatico
public interface PatientRepository extends GenericRepository<Patient, Integer>
{
}
