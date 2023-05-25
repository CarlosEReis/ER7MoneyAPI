package com.carloser7.er7money.api.service;

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
import org.springframework.stereotype.Service;

import com.carloser7.er7money.api.dto.LancamentoEstatisticaPessoa;
import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.repository.LancamentoRepository;
import com.carloser7.er7money.api.service.exception.RecursoInexistenteException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	public Lancamento salvar(Lancamento lancamento) {
		this.pessoaService.validaPessoa(lancamento.getPessoa());
		return this.lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoBanco = buscaLancamento(codigo);
		
		this.pessoaService.validaPessoa(lancamento.getPessoa());
		this.categoriaService.validaCategoria(lancamento.getCategoria());
		
		BeanUtils.copyProperties(lancamento, lancamentoBanco, "codigo");
		return this.lancamentoRepository.save(lancamentoBanco);
	}

	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = this.lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIAL", Date.valueOf(inicio));
		parametros.put("DT_FINAL", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa-01.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	private Lancamento buscaLancamento(Long codigo) {
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
		if (lancamento.isEmpty()) {
			throw new RecursoInexistenteException();
		}
		return lancamento.get();
	}
	
}
