package com.mitocode.controller;

import com.mitocode.dto.MedicDto;
import com.mitocode.model.Medic;
import com.mitocode.service.MedicService;
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
@RequestMapping("/medics")
public class MedicController
{
    private final MedicService medicService;

    //@Qualifier("medicMapper")
    //private final ModelMapper modelMapper;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MedicDto>> findAll()
    {
        List<MedicDto> list = mapperUtil.mapList(medicService.findAll(), MedicDto.class, "medicMapper");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDto> findById(@PathVariable("id") Integer id)
    {
        Medic obj = medicService.findById(id);
        return ResponseEntity.ok(mapperUtil.map(obj, MedicDto.class, "medicMapper"));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MedicDto medicDto)
    {
        Medic obj = medicService.save(mapperUtil.map(medicDto, Medic.class, "medicMapper"));
        URI location = (ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDto> update(@PathVariable("id") Integer id,@Valid @RequestBody MedicDto medicDto)
    {
        Medic obj = medicService.update(id, mapperUtil.map(medicDto, Medic.class, "medicMapper"));
        return ResponseEntity.ok(mapperUtil.map(obj, MedicDto.class));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
    {
        medicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDto> findByIdHateoas(@PathVariable("id") Integer id)
    {
        Medic obj = medicService.findById(id);
        EntityModel<MedicDto> resource = EntityModel.of(mapperUtil.map(obj,MedicDto.class, "medicMapper"));
        //Generar links informativos
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("medic-self-info"));
        resource.add(link2.withRel("medic-all-info"));

        return resource;

    }

}
