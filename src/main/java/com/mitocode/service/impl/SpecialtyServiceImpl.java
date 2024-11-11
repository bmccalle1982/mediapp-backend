package com.mitocode.service.impl;

import com.mitocode.model.Specialty;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.SpecialtyRepository;
import com.mitocode.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends  CRUDServiceImpl<Specialty, Integer> implements SpecialtyService
{
    private final SpecialtyRepository specialtyRepository;
    @Override
    protected GenericRepository<Specialty, Integer> getRepository() {
        return specialtyRepository;
    }
}
