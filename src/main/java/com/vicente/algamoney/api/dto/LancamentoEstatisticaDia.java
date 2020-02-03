package com.vicente.algamoney.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vicente.algamoney.api.model.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoEstatisticaDia {
	
	private TipoLancamento tipoLancamento;
	
	private LocalDate dia;
	
	private BigDecimal total;

}
