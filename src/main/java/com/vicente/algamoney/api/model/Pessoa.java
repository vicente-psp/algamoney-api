package com.vicente.algamoney.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String nome;
	
	@NotNull
	private Boolean ativo;
	
	@Embedded @Valid
	private Endereco endereco;
	
	@JsonIgnore
	@Transient
	public Boolean isInativo() {
		return !this.ativo;
	}
	
}
