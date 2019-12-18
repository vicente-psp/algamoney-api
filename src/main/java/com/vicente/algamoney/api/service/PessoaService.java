package com.vicente.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired private PessoaRepository repository;
	
	public Pessoa update(Long id, Pessoa entity) {
		Pessoa pessoa = findPessoaById(id);
		BeanUtils.copyProperties(entity, pessoa, "id");
		return repository.save(pessoa);
	}

	public void updatePropertyAtivo(Long id, Boolean ativo) {
		Pessoa entity = findPessoaById(id);
		entity.setAtivo(ativo);
		repository.save(entity);
	}

	private Pessoa findPessoaById(Long id) {
		Optional<Pessoa> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return optional.get();
	}

}
