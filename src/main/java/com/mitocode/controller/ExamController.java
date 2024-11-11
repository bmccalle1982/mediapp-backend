package com.mitocode.controller;

import com.mitocode.dto.ExamDto;
import com.mitocode.model.Exam;
import com.mitocode.service.ExamService;
import com.mitocode.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController
{
    private final ExamService examService;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<ExamDto>> findAll()
    {
        List<ExamDto> list = mapperUtil.mapList(examService.findAll(), ExamDto.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> findById(@PathVariable("id") Integer id)
    {
        Exam obj = examService.findById(id);
        return ResponseEntity.ok(mapperUtil.map(obj, ExamDto.class));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ExamDto examDto)
    {
        Exam obj = examService.save(mapperUtil.map(examDto, Exam.class));
        URI location = (ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDto> update(@PathVariable("id") Integer id,@Valid @RequestBody ExamDto examDto)
    {
        Exam obj = examService.update(id, mapperUtil.map(examDto, Exam.class));
        return ResponseEntity.ok(mapperUtil.map(obj, ExamDto.class));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
    {
        examService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDto> findByIdHateoas(@PathVariable("id") Integer id)
    {
        Exam obj = examService.findById(id);
        EntityModel<ExamDto> resource = EntityModel.of(mapperUtil.map(obj,ExamDto.class));
        //Generar links informativos
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("exam-self-info"));
        resource.add(link2.withRel("exam-all-info"));

        return resource;

    }

}
