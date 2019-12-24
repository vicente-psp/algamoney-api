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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.vicente.algamoney.api.model.Categoria_;
import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.model.Lancamento_;
import com.vicente.algamoney.api.model.Pessoa_;
import com.vicente.algamoney.api.repository.filter.LancamentoFilter;
import com.vicente.algamoney.api.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		
		criteriaQuery.where(predicates);
		TypedQuery<Lancamento> typedQuery = this.entityManager.createQuery(criteriaQuery);
		
		adicionarRestricaoDePaginacao(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(lancamentoFilter));
	}

	@Override
	public Page<ResumoLancamento> resumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteriaQuery = criteriaBuilder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(
													ResumoLancamento.class,
													root.get(Lancamento_.id),
													root.get(Lancamento_.descricao),
													root.get(Lancamento_.dataVencimento),
													root.get(Lancamento_.dataPagamento),
													root.get(Lancamento_.valor),
													root.get(Lancamento_.tipo),
													root.get(Lancamento_.categoria).get(Categoria_.nome),
													root.get(Lancamento_.pessoa).get(Pessoa_.nome)
												));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		
		criteriaQuery.where(predicates);
		TypedQuery<ResumoLancamento> typedQuery = this.entityManager.createQuery(criteriaQuery);
		
		adicionarRestricaoDePaginacao(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(lancamentoFilter));
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

	private void adicionarRestricaoDePaginacao(TypedQuery<?> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(totalRegistroPorPagina);
	}

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(criteriaBuilder.count(root));
		return this.entityManager.createQuery(criteriaQuery).getSingleResult();
	}

}
