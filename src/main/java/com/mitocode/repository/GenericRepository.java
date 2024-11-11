package com.mitocode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean//Spring anule esta interfaz
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>
{
}
