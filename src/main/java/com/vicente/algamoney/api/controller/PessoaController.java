package com.vicente.algamoney.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.vicente.algamoney.api.event.RecursoCriadoEvent;
import com.vicente.algamoney.api.generics.GenericOperationsController;
import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController implements GenericOperationsController<Pessoa> {
	
	@Autowired private PessoaRepository pessoaRepository;
	
	@Autowired private ApplicationEventPublisher publisher;
	
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
	public ResponseEntity<Pessoa> post(@Valid @RequestBody Pessoa entity, HttpServletResponse response) {
		Pessoa savedEntity = pessoaRepository.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, savedEntity.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
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
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}

}
