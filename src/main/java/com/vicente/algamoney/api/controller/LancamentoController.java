package com.vicente.algamoney.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.algamoney.api.event.RecursoCriadoEvent;
import com.vicente.algamoney.api.exceptionhandler.AlgaMoneyExceptionHandler.Erro;
import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.repository.LancamentoRepository;
import com.vicente.algamoney.api.repository.filter.LancamentoFilter;
import com.vicente.algamoney.api.repository.projection.ResumoLancamento;
import com.vicente.algamoney.api.service.LancamentoService;
import com.vicente.algamoney.api.service.exception.PessoaInativaException;
import com.vicente.algamoney.api.service.exception.PessoaInexistenteException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired private LancamentoRepository repository;
	
	@Autowired private LancamentoService service;
	
	@Autowired private ApplicationEventPublisher publisher;
	
	@Autowired private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> get(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return repository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> getResumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return repository.resumo(lancamentoFilter, pageable);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> get(@PathVariable Long id) {
		Optional<Lancamento> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		Lancamento lancamento = optional.get();
		return ResponseEntity.ok().body(lancamento);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> post(@Valid @RequestBody Lancamento entity, HttpServletResponse response) {
		Lancamento savedEntity = service.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, savedEntity.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	@ExceptionHandler({PessoaInexistenteException.class})
	public ResponseEntity<Object> handlePessoaInexistenteException(PessoaInexistenteException ex) {
		String userMessage = messageSource.getMessage("pessoa.inexistente", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Erro> errList = Arrays.asList(new Erro(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errList);
	}
	
	@ExceptionHandler({PessoaInativaException.class})
	public ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex) {
		String userMessage = messageSource.getMessage("pessoa.inativa", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Erro> errList = Arrays.asList(new Erro(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errList);
	}

}
