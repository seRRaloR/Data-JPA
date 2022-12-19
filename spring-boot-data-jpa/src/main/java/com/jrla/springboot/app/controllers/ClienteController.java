package com.jrla.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jrla.springboot.app.models.dao.IClienteDao;
import com.jrla.springboot.app.models.entities.Cliente;

@Controller
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	//@RequestMapping(value="/listar", method=RequestMethod.GET)
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "lista-clientes";
	}

	// Por defecto, el valor de method es RequestMethod.GET
	//@RequestMapping(value="/alta-cliente")
	@GetMapping("/alta-cliente")
	public String altaCliente(Map<String, Object> modelo) {
		Cliente cliente = new Cliente();
		
		modelo.put("titulo", "Formulario de alta de clientes");
		modelo.put("cliente", cliente);

		return "alta-cliente";
	}

	@RequestMapping(value="/alta-cliente", method = RequestMethod.POST)
	public String guardar(Cliente cliente) {
		clienteDao.save(cliente);
		
		return "redirect:listar";
	}
}
