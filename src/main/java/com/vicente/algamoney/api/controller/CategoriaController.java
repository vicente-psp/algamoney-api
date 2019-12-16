package com.vicente.algamoney.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vicente.algamoney.api.generics.GenericOperationsController;
import com.vicente.algamoney.api.model.Categoria;
import com.vicente.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements GenericOperationsController<Categoria> {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> get() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> get(@PathVariable Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (optional.isPresent()) {
			Categoria entity = optional.get();
			return ResponseEntity.ok().body(entity);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria entity) {
		Categoria savedEntity = categoriaRepository.save(entity);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedEntity.getId()).toUri();
		
		return ResponseEntity.created(uri).body(savedEntity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> put(@PathVariable Long id, @Valid @RequestBody Categoria entity) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		if (optional.isPresent()) {
			Categoria categoria = optional.get();
			
			BeanUtils.copyProperties(entity, categoria, "id");
			
			categoria = categoriaRepository.save(categoria);
			
			return ResponseEntity.ok(categoria);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (optional.isPresent()) {
			categoriaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
