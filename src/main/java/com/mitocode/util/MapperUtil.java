package com.mitocode.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

//Cuando estereotipamos una clase, Spring tendrá un bean como inyección de indepencias
@Component
@RequiredArgsConstructor
public class MapperUtil
{
    //Necesito invocar a un objeto en tiempo de ejecucion de forma dinámica
    private final ApplicationContext applicationContext;//Ingreso al container y saco al que necesito
    //Defino 2 genéricos (S,T)
    //Cuando envío String... = String[] , este parámetro signfica que puede recibir uno o más qualifier
    public <S,T> List<T> mapList(List<S> source,Class<T> targetClass,String... mapperQualifier)
    {
        ModelMapper modelMapper = getModelMapper(mapperQualifier);
        //De este source, aplicaremos stream, ese elemento lo voy a transforma a la clase que necesito
        // y todo eso lo transformo a lista
        return source
                .stream()
                .map( element -> modelMapper.map(element, targetClass))
                 .toList();
    }

    public <S,T> T map(S source, Class<T> targetClass,String... mapperQualifier)
    {
        ModelMapper modelMapper = getModelMapper(mapperQualifier);
        return modelMapper.map(source,targetClass);
    }

    private ModelMapper getModelMapper(String... mapperQualifier)
    {
        if(mapperQualifier.length == 0 || mapperQualifier[0] == null || mapperQualifier[0].isEmpty())
        {
            return applicationContext.getBean("defaultMapper", ModelMapper.class);
        }
        else
        {
            return applicationContext.getBean(mapperQualifier[0], ModelMapper.class);
        }
    }

}
