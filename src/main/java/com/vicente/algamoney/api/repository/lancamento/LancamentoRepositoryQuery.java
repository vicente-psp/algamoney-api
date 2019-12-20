package com.vicente.algamoney.api.repository.lancamento;

import java.util.List;

import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
