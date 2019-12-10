package com.vicente.algamoney.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.algamoney.api.model.Categoria;
import com.vicente.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listAll() {
		return categoriaRepository.findAll();
	}
	
}
