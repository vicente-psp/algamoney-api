package com.vicente.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
