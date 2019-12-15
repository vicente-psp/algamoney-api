package com.vicente.algamoney.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vicente.algamoney.api.model.Categoria;
import com.vicente.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listAll() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (optional.isPresent()) {
			Categoria categoria = optional.get();
			return ResponseEntity.ok().body(categoria);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@RequestBody Categoria entity) {
		Categoria savedEntity = categoriaRepository.save(entity);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
		.buildAndExpand(savedEntity.getId()).toUri();
		
		return ResponseEntity.created(uri).body(savedEntity);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
	}
	
}
