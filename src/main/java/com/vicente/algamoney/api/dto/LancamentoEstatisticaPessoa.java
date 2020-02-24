package com.vicente.algamoney.api.dto;

import java.math.BigDecimal;

import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.model.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoEstatisticaPessoa {
	
	private TipoLancamento tipo;
	
	private Pessoa pessoa;
	
	private BigDecimal total;

}
