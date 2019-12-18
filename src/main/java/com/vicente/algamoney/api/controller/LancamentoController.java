package com.vicente.algamoney.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.algamoney.api.generics.GenericOperationsController;
import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController implements GenericOperationsController<Lancamento> {

	@Autowired private LancamentoRepository repository;

	@GetMapping
	public List<Lancamento> get() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> get(@PathVariable Long id) {
		Optional<Lancamento> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		Lancamento lancamento = optional.get();
		return ResponseEntity.ok().body(lancamento);
	}
	
	@Override
	public ResponseEntity<Lancamento> post(Lancamento entity, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Lancamento> put(Long id, Lancamento entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
