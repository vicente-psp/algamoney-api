package com.vicente.algamoney.api.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vicente.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.vicente.algamoney.api.dto.LancamentoEstatisticaDia;
import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.repository.filter.LancamentoFilter;
import com.vicente.algamoney.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumo(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
