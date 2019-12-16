package com.vicente.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
