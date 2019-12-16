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
import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController implements GenericOperationsController<Pessoa> {
	
	@Autowired private PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> get() {
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> get(@PathVariable Long id) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if (optional.isPresent()) {
			Pessoa entity = optional.get();
			return ResponseEntity.ok().body(entity);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> post(@Valid @RequestBody Pessoa entity) {
		Pessoa savedEntity = pessoaRepository.save(entity);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedEntity.getId()).toUri();

		return ResponseEntity.created(uri).body(savedEntity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> put(@PathVariable Long id, @Valid @RequestBody Pessoa entity) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		
		if (optional.isPresent()) {
			Pessoa pessoa = optional.get();
			
			BeanUtils.copyProperties(entity, pessoa, "id");
			
			pessoa = pessoaRepository.save(pessoa);
			
			return ResponseEntity.ok(pessoa);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if (optional.isPresent()) {
			pessoaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.notFound().build();
	}

}
