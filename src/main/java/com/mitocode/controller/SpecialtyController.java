package com.mitocode.controller;

import com.mitocode.dto.SpecialtyDto;
import com.mitocode.model.Specialty;
import com.mitocode.service.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/specialties") //Esten expresados en sustantivos y en plural
public class SpecialtyController
{
    private final SpecialtyService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDto>> findAll()
    {
        List<SpecialtyDto> list = service.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDto> findById(@PathVariable("id") Integer id)
    {
        Specialty obj = service.findById(id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SpecialtyDto specialtyDto)
    {
        Specialty obj = service.save(convertToEntity(specialtyDto));
        URI location = (ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(obj.getIdSpecialty()).toUri());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDto> update(@PathVariable("id") Integer id,@Valid @RequestBody SpecialtyDto specialtyDto)
    {
        Specialty obj  = service.update(id, convertToEntity(specialtyDto));
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDto> findByIdHateoas(@PathVariable("id") Integer id)
    {
        Specialty obj = service.findById(id);
        EntityModel<SpecialtyDto> resource = EntityModel.of(convertToDto(obj));
        //generar link informativos
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder webMvcLinkBuilder2 = linkTo(methodOn(this.getClass()).findAll());
        resource.add(webMvcLinkBuilder.withRel("specialty-self-info"));
        resource.add(webMvcLinkBuilder2.withRel("specialty-all-info"));
        return resource;
    }

    private  SpecialtyDto convertToDto(Specialty specialty)
    {
        return modelMapper.map(specialty, SpecialtyDto.class);
    }

    private Specialty convertToEntity(SpecialtyDto specialtyDto)
    {
        return modelMapper.map(specialtyDto, Specialty.class);
    }

}
