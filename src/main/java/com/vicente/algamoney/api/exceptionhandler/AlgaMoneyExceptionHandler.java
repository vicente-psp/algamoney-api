package com.vicente.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
public class AlgaMoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String userMessage = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.getCause().toString();
		List<Erro> errList = Arrays.asList(new Erro(userMessage, developerMessage));
		return handleExceptionInternal(ex, errList, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> errList = createErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, errList, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Erro> errList = Arrays.asList(new Erro(userMessage, developerMessage));
		return handleExceptionInternal(ex, errList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	private List<Erro> createErrorList(BindingResult bindingResult) {
		List<Erro> errList = new ArrayList<>();
		
		bindingResult.getFieldErrors().forEach(fieldErro -> {
			String userMessage = messageSource.getMessage(fieldErro, LocaleContextHolder.getLocale());
			String developerMessage = fieldErro.toString();
			errList.add(new Erro(userMessage, developerMessage));
		});
		
		return errList;
	}
	
	@Getter @Setter
	@AllArgsConstructor
	private static class Erro {
		
		private String userMessage;
		private String developerMessage;
		
	}

}
