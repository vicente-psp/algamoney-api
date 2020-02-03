package com.vicente.algamoney.api.dto;

import java.math.BigDecimal;

import com.vicente.algamoney.api.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoEstatisticaCategoria {

	private Categoria categoria;
	
	private BigDecimal total;
	
}
