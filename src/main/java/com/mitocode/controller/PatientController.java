package com.mitocode.controller;

import com.mitocode.dto.PatientDto;
import com.mitocode.model.Patient;
import com.mitocode.service.PatientService;
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
@RequestMapping("/patients") //Esten expresados en sustantivos y en plural
//@CrossOrigin( origins = "*")
public class PatientController
{
    private final PatientService service;

    //@Qualifier("defaultMapper")
   // private final ModelMapper modelMapper;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<PatientDto>> findAll()
    {
        //List<PatientDto> list = service.findAll().stream().map ( e -> new PatientDto(e.getIdPatient(), e.getFirstName(),e.getLastName(),e.getDni(),e.getAddress(),e.getPhone(),e.getEmail())).toList();
        //List<PatientDto> list = service.findAll().stream().map( e -> modelMapper.map(e, PatientDto.class)).toList();
        //List<PatientDto> list = service.findAll().stream().map( e -> convertToDto(e)).toList(); // e -> convertToDto(e)
        //List<PatientDto> list = service.findAll().stream().map(this::convertToDto).toList();
        List<PatientDto> list = mapperUtil.mapList(service.findAll(), PatientDto.class);
        return ResponseEntity.ok(list);
        //Otra forma
        //return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findById(@PathVariable("id") Integer id)
    {
        Patient obj = service.findById(id);
        // return ResponseEntity.ok(modelMapper.map(obj, PatientDto.class));
        // return ResponseEntity.ok(convertToDto(obj));//Referencia a metodos solo con expresiones lambda
        return ResponseEntity.ok(mapperUtil.map(obj, PatientDto.class));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDto patientDto)
    {
        //Patient obj = service.save(convertToEntity(patientDto));
        Patient obj = service.save(mapperUtil.map(patientDto, Patient.class));
        //localhost:8080/patients/{id}
        URI location = (ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(obj.getIdPatient()).toUri());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> update(@PathVariable("id") Integer id,@Valid @RequestBody PatientDto patientDto)
    {
        Patient obj  = service.update(id, mapperUtil.map(patientDto,Patient.class));
        return ResponseEntity.ok(mapperUtil.map(obj, PatientDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDto> findByIdHateoas(@PathVariable("id") Integer id)
    {
        Patient obj = service.findById(id);
        EntityModel<PatientDto> resource = EntityModel.of(mapperUtil.map(obj,PatientDto.class));
        //generar link informativos
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder webMvcLinkBuilder2 = linkTo(methodOn(this.getClass()).findAll());
        resource.add(webMvcLinkBuilder.withRel("patient-self-info"));
        resource.add(webMvcLinkBuilder2.withRel("patient-all-info"));
        return resource;
    }
/*
    private  PatientDto convertToDto(Patient patient)
    {
        //return modelMapper.map(patient, PatientDto.class);
        return null;
    }

    private Patient convertToEntity(PatientDto patientDto)
    {
        //return modelMapper.map(patientDto, Patient.class);
        return null;
    }
*/
}
