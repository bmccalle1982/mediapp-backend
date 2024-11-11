package com.mitocode.service.impl;

import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repository.ConsultExamRepository;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.ConsultRepository;
import com.mitocode.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDServiceImpl<Consult, Integer> implements ConsultService
{
    private final ConsultRepository consultRepository;
    private final ConsultExamRepository consultExamRepository;

    @Override
    protected GenericRepository<Consult, Integer> getRepository() {
        return consultRepository;
    }

    @Override
    @Transactional
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        //El objeto consulta tiene el detalle de manera interna, gracias a que consulta tiene referencia en cascada

        consultRepository.save(consult); // guarda el maestro detalle
        exams.forEach( exam -> consultExamRepository.saveExam(consult.getIdConsult(), exam.getIdExam()));
        return consult;
    }
}
