package com.vicente.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.LancamentoRepository;
import com.vicente.algamoney.api.repository.PessoaRepository;
import com.vicente.algamoney.api.service.exception.PessoaInativaException;
import com.vicente.algamoney.api.service.exception.PessoaInexistenteException;

@Service
public class LancamentoService {
	
	@Autowired private LancamentoRepository LancamentoRepository;
	
	@Autowired private PessoaRepository pessoaRepository;
	
	public Lancamento save(Lancamento entity) {
		Optional<Pessoa> pOptional = pessoaRepository.findById(entity.getPessoa().getId());
		if (pOptional.isPresent()) {
			Pessoa pessoa = pOptional.get();
			if (pessoa.isInativo()) {
				throw new PessoaInativaException();
			}
		} else {
			throw new PessoaInexistenteException();
		}
		
		return LancamentoRepository.save(entity);
	}

}
