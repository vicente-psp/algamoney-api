package com.vicente.algamoney.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vicente.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.vicente.algamoney.api.model.Lancamento;
import com.vicente.algamoney.api.model.Pessoa;
import com.vicente.algamoney.api.repository.LancamentoRepository;
import com.vicente.algamoney.api.repository.PessoaRepository;
import com.vicente.algamoney.api.service.exception.PessoaInativaException;
import com.vicente.algamoney.api.service.exception.PessoaInexistenteException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	@Autowired private LancamentoRepository lancamentoRepository;
	
	@Autowired private PessoaRepository pessoaRepository;
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	public Lancamento save(Lancamento entity) {
		validaPessoa(entity.getPessoa().getId(), true);
		return lancamentoRepository.save(entity);
	}
	
	public Lancamento update(Long id, Lancamento entity) {
		Optional<Lancamento> optional = lancamentoRepository.findById(id);
		if (!optional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		Lancamento lancamento = optional.get();
		validaPessoa(entity.getPessoa().getId(), lancamento.getPessoa().getId() != entity.getPessoa().getId());
		BeanUtils.copyProperties(entity, lancamento, "id");
		entity.setId(id);
		return lancamentoRepository.save(entity);
	}

	private void validaPessoa(Long id, boolean validaAtivo) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if (!optional.isPresent()) {
			throw new PessoaInexistenteException();
		} else if(validaAtivo) {
			Pessoa pessoa = optional.get();
			if (pessoa.isInativo()) {
				throw new PessoaInativaException();
			}
		}
	}

}
