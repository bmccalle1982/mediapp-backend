package com.mitocode.service.impl;

import com.mitocode.model.Medic;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.MedicRepository;
import com.mitocode.service.MedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDServiceImpl<Medic, Integer> implements MedicService
{
    private final MedicRepository medicRepository;

    @Override
    protected GenericRepository<Medic, Integer> getRepository() {
        return medicRepository;
    }

}
