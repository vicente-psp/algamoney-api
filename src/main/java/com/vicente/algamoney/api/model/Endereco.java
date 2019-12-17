package com.vicente.algamoney.api.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Endereco {
	
	@Size(min = 3, max = 30)
	private String logradouro;
	
	@Size(min = 1, max = 30)
	private String numero;
	
	@Size(min = 3, max = 30)
	private String complemento;
	
	@Size(min = 3, max = 30)
	private String bairro;
	
	@Size(min = 8, max = 10)
	private String cep;
	
	@Size(min = 3, max = 30)
	private String cidade;
	
	@Size(min = 2, max = 30)
	private String estado;

}
