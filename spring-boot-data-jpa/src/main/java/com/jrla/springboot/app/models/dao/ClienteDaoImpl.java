package com.jrla.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jrla.springboot.app.models.entities.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}

	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		if (cliente.getId() == null) {
			em.persist(cliente);
		} else if (cliente.getId() > 0) {
			em.merge(cliente);
		}

	}

	@Override
	public void delete(Long id) {
		em.remove(this.findOne(id));
		
	}

}
