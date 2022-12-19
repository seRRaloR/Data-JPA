package com.jrla.springboot.app.models.dao;

import java.util.List;

import com.jrla.springboot.app.models.entities.Cliente;

public interface IClienteDao {

	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
}
