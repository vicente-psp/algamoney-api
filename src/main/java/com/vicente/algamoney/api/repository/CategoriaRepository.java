package com.vicente.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.algamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
