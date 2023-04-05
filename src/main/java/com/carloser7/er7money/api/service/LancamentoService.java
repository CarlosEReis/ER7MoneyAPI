package com.carloser7.er7money.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.repository.LancamentoRepository;
import com.carloser7.er7money.api.service.exception.RecursoInexistenteException;

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

	private Lancamento buscaLancamento(Long codigo) {
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
		if (lancamento.isEmpty()) {
			throw new RecursoInexistenteException();
		}
		return lancamento.get();
	}


 

	

	
}
