package com.vicente.algamoney.api.generics;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;


public interface GenericOperationsController<E> {
	
	ResponseEntity<E> post(E entity, HttpServletResponse response);
	ResponseEntity<E> get(Long id);
	
	void delete(Long id);
	
}