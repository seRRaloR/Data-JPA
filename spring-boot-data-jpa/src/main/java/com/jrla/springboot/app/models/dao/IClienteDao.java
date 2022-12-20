package com.jrla.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jrla.springboot.app.models.entities.Cliente;

/*
 * No hce falta anotar con @Component porque hereda de CrudRepository
 * (https://stackoverflow.com/questions/44069367/repository-not-necessary-when-implementing-jparepository)
 */
public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
