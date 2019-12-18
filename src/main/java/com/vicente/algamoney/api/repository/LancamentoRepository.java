package com.vicente.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.algamoney.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
