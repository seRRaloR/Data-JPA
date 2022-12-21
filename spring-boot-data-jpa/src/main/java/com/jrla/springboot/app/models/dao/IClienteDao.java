package com.jrla.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jrla.springboot.app.models.entities.Cliente;

/*
 * No hce falta anotar con @Component porque hereda de CrudRepository
 * (https://stackoverflow.com/questions/44069367/repository-not-necessary-when-implementing-jparepository)
 * 
 *  Para la paginación, heredamos de JpaRepository, que a su vez hereda de ListCrudRepository
 */
public interface IClienteDao extends JpaRepository<Cliente, Long>{

}
