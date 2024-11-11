package com.mitocode.config;

import com.mitocode.dto.MedicDto;
import com.mitocode.model.Medic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig
{
    //Con este Bean sabe que tiene que inyectar esta instancia en su contenedor
    //Este bean devuelve algo del modelmapper, retornamos la instancia
    //para que Spring te lo pueda dar a través de una inyección de dependencias
    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper()
    {
        return new ModelMapper();
    }

    //Personalizar el bean para escribir y leer los datos
    @Bean(name = "medicMapper")
    public ModelMapper medicMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        //Necesito definir un proceso de escritura, para evitar el error en POST o PUT
        //Le indico las reglas de la estructura de mi mapper
        modelMapper.createTypeMap(MedicDto.class, Medic.class)
                .addMapping(MedicDto::getPrimaryName, (destination, value) -> destination.setFirstName((String) value))
                .addMapping(MedicDto::getSurname, (destination, value) -> destination.setLastName((String) value))
                .addMapping(MedicDto::getPhoto, (destination, value) -> destination.setPhotoUrl((String) value));

        //Ahora realizamos para la lectura
        modelMapper.createTypeMap(Medic.class, MedicDto.class)
                .addMapping(Medic::getFirstName, (destination, value) -> destination.setPrimaryName((String) value))
                .addMapping(Medic::getLastName, (destination, value) -> destination.setSurname((String) value))
                .addMapping(Medic::getPhotoUrl, (destination, value) -> destination.setPhoto((String) value));

        return modelMapper;
    }



}
