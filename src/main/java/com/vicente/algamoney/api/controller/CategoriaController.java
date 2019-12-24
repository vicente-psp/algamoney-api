package com.vicente.algamoney.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.algamoney.api.event.RecursoCriadoEvent;
import com.vicente.algamoney.api.generics.GenericOperationsController;
import com.vicente.algamoney.api.model.Categoria;
import com.vicente.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements GenericOperationsController<Categoria> {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@Autowired private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> get() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> get(@PathVariable Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (!optional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		Categoria entity = optional.get();
		return ResponseEntity.ok().body(entity);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria entity, HttpServletResponse response) {
		Categoria savedEntity = categoriaRepository.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, savedEntity.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
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
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
	}
	
}
