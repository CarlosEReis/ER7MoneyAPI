package com.carloser7.er7money.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.carloser7.er7money.api.dto.LancamentoEstatisticaPessoa;
import com.carloser7.er7money.api.mail.Mailer;
import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.model.Usuario;
import com.carloser7.er7money.api.repository.LancamentoRepository;
import com.carloser7.er7money.api.repository.UsuarioRepository;
import com.carloser7.er7money.api.service.exception.RecursoInexistenteException;
import com.carloser7.er7money.api.storage.S3;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private Mailer mailer;
		
	@Autowired
	private S3 s3;
	
	public Lancamento salvar(Lancamento lancamento) {
		this.pessoaService.validaPessoa(lancamento.getPessoa());
		
		if (StringUtils.hasText(lancamento.getAnexo())) {
			s3.salvar(lancamento.getAnexo());
		}
		
		return this.lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoBanco = buscaLancamento(codigo);
		
		this.pessoaService.validaPessoa(lancamento.getPessoa());
		this.categoriaService.validaCategoria(lancamento.getCategoria());
		
		if (!StringUtils.hasText(lancamento.getAnexo()) && StringUtils.hasText(lancamentoBanco.getAnexo())) {
			this.s3.remover(lancamentoBanco.getAnexo());
		} else if (StringUtils.hasText(lancamento.getAnexo()) && !lancamento.getAnexo().equals(lancamentoBanco.getAnexo())) {
			this.s3.substituir(lancamentoBanco.getAnexo(), lancamento.getAnexo());
		}
		
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
	
  	@Scheduled(cron = "0 0 6 * * *")
	//@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void scheduler() {
  		if (logger.isDebugEnabled()) logger.debug("\nPreparando envio de e-mails de aviso de lançamentos vencidos.\n");
		
		List<Lancamento> lancamentosVencidos = this.lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		if ( lancamentosVencidos.isEmpty()) {
			logger.info("\nSem lançamentos vencidos para aviso.\n");
			return;
		}
		logger.info("\nExistem {} lançamentos vencidos.\n", lancamentosVencidos.size());
		
		List<Usuario> destinatarios= this.usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		if (destinatarios.isEmpty()) {
			logger.warn("\nExistem lançamentos vencidos, mas o sistema não encontrou destinarários.\n");
			return;
		}
		
		this.mailer.avisarSobreLancamentosVencidos(lancamentosVencidos, destinatarios);
		logger.info("\nEnvio de e-mail de aviso concluído.\n");
  	}
	
	private Lancamento buscaLancamento(Long codigo) {
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
		if (lancamento.isEmpty()) {
			throw new RecursoInexistenteException();
		}
		return lancamento.get();
	}
	
}
