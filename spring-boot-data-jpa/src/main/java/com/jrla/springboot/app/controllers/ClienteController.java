package com.jrla.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jrla.springboot.app.models.entities.Cliente;
import com.jrla.springboot.app.models.service.ClienteServiceImpl;
import com.jrla.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private ClienteServiceImpl clienteService;
	
	@Value("${paginacion.numero.elementos.pagina}")
	private int numElementosPorPagina;
	
	@GetMapping("/detalle-cliente/{id}")
	public String detalle(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		
		if (cliente == null) {
			flash.addFlashAttribute("errorMessage","El cliente no existe!");
			return "redirect:/listar";
		}
		
		modelo.put("titulo", "Detalle cliente: " + cliente.getNombre());
		modelo.put("cliente", cliente);
		
		
		return "detalle-cliente";
	}
	
	//@RequestMapping(value="/listar", method=RequestMethod.GET)
	@GetMapping("/listar")
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, numElementosPorPagina);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
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
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Cliente cliente = null;
		
		if (id != null && id>0) {
			cliente = clienteService.findOne(id);
			
			if (cliente == null) {
				flash.addFlashAttribute("errorMessage","El cliente no existe!");
				return "redirect:/listar";
			}
			
			modelo.put("titulo","Edición de cliente");
			modelo.put("cliente",cliente);
		} else {
			flash.addFlashAttribute("errorMessage","El ID del cliente no es correcto!");
			return "redirect:/listar";
		}
		
		return "alta-cliente";
	}

	@RequestMapping(value="/alta-cliente", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente
			, BindingResult resultado
			, Model modelo
			, @RequestParam("file") MultipartFile foto
			,RedirectAttributes flash
			, SessionStatus status) {
		
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Nuevo Cliente");
			return "alta-cliente";
		}
		
		if (!foto.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath.concat("//").concat(foto.getOriginalFilename()));
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("infoMessage","Archivo '".concat(foto.getOriginalFilename()).concat("' cargado correctamente!"));
				cliente.setFoto(foto.getOriginalFilename());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (cliente.getId() == null)? "creado" : "modificado";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("successMessage","Cliente ".concat(mensajeFlash).concat(" correctamente!"));
		return "redirect:/listar";
	}

	@RequestMapping(value="/baja-cliente/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if (id != null && id>0) {
			clienteService.delete(id);
		}
		
		flash.addFlashAttribute("successMessage","Cliente eliminado correctamente!");
		return "redirect:/listar";
	}

}
