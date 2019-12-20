package com.vicente.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.model.Lancamento_;
import com.vicente.algamoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		
		criteriaQuery.where(predicates);
		
		TypedQuery<Lancamento> typedQuery = this.entityManager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(
					criteriaBuilder.like(
							criteriaBuilder.lower(root.get(Lancamento_.descricao)),
							"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
					)
			);
		}
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					criteriaBuilder.greaterThanOrEqualTo(
							root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()
					)
			);
		}
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					criteriaBuilder.lessThanOrEqualTo(
							root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()
					)
			);
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
