package com.mitocode.service.impl;

import com.mitocode.model.Exam;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.ExamRepository;
import com.mitocode.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDServiceImpl<Exam, Integer> implements ExamService
{
    private final ExamRepository examRepository;

    @Override
    protected GenericRepository<Exam, Integer> getRepository() {
        return examRepository;
    }

}
