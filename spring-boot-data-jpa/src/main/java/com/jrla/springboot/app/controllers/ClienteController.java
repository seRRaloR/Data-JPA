package com.jrla.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jrla.springboot.app.models.dao.IClienteDao;
import com.jrla.springboot.app.models.entities.Cliente;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
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
		
		modelo.put("titulo", "Nuevo cliente");
		modelo.put("cliente", cliente);

		return "alta-cliente";
	}

	@RequestMapping(value="/alta-cliente/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> modelo) {
		Cliente cliente = null;
		
		if (id != null && id>0) {
			cliente = clienteDao.findOne(id);
			modelo.put("titulo","Edición de cliente");
			modelo.put("cliente",cliente);
		} else {
			return "redirect:listar";
		}
		
		return "alta-cliente";
	}

	@RequestMapping(value="/alta-cliente", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult resultado, Model modelo, SessionStatus status) {
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Nuevo Cliente");
			return "alta-cliente";
		}
		
		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:/listar";
	}

	@RequestMapping(value="/baja-cliente/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if (id != null && id>0) {
			clienteDao.delete(id);
		}
		
		return "redirect:/listar";
	}

}
