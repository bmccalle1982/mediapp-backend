package com.mitocode.service.impl;

import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.repository.GenericRepository;
import com.mitocode.service.CRUDService;

import java.util.List;

public abstract class CRUDServiceImpl<T, ID> implements CRUDService<T, ID>
{
    //Un método abstracto devuelve algo compatible
    protected  abstract GenericRepository<T, ID> getRepository();
    @Override
    public T save(T t) {
        return getRepository().save(t);
    }

    @Override
    public T update(ID id, T t) {
        getRepository().findById(id).orElseThrow( () -> new ModelNotFoundException("ID not found: " + id));
        //t.setId Java Reflection
        return getRepository().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id).orElseThrow( () -> new ModelNotFoundException("ID not found: " + id));
    }

    @Override
    public void delete(ID id) {
        getRepository().findById(id).orElseThrow( () -> new ModelNotFoundException("ID not found: " + id));
        getRepository().deleteById(id);
    }
}
