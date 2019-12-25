package com.vicente.algamoney.api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
	
}
