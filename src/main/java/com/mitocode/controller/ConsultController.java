package com.mitocode.controller;

import com.mitocode.dto.ConsultDto;
import com.mitocode.dto.ConsultListExamDto;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.ConsultService;
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
@RequestMapping("/consults")
public class ConsultController
{
    private final ConsultService consultService;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<ConsultDto>> findAll()
    {
        List<ConsultDto> list = mapperUtil.mapList(consultService.findAll(), ConsultDto.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDto> findById(@PathVariable("id") Integer id)
    {
        Consult obj = consultService.findById(id);
        return ResponseEntity.ok(mapperUtil.map(obj, ConsultDto.class));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultListExamDto consultDto)
    {
        Consult consult = mapperUtil.map(consultDto.getConsult(), Consult.class);
        List<Exam> exams = mapperUtil.mapList(consultDto.getListExams(), Exam.class);
        Consult obj = consultService.saveTransactional(consult,exams);

        URI location = (ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDto> update(@PathVariable("id") Integer id,@Valid @RequestBody ConsultDto consultDto)
    {
        Consult obj = consultService.update(id, mapperUtil.map(consultDto, Consult.class));
        return ResponseEntity.ok(mapperUtil.map(obj, ConsultDto.class));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
    {
        consultService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDto> findByIdHateoas(@PathVariable("id") Integer id)
    {
        Consult obj = consultService.findById(id);
        EntityModel<ConsultDto> resource = EntityModel.of(mapperUtil.map(obj,ConsultDto.class));
        //Generar links informativos
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("consult-self-info"));
        resource.add(link2.withRel("consult-all-info"));

        return resource;

    }

}
