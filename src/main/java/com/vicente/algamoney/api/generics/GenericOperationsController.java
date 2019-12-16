package com.vicente.algamoney.api.generics;

import java.util.List;

import org.springframework.http.ResponseEntity;


public interface GenericOperationsController<E> {
	
	ResponseEntity<E> post(E entity);
	ResponseEntity<E> get(Long id);
	ResponseEntity<E> put(Long id, E entity);
	ResponseEntity<?> delete(Long id);
	
	List<E> get();

}