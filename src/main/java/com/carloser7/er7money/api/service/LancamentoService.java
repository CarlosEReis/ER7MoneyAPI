package com.carloser7.er7money.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.model.Pessoa;
import com.carloser7.er7money.api.repository.LancamentoRepository;
import com.carloser7.er7money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = this.pessoaService.buscarPessoaPeloCodigo(lancamento.getPessoa().getCodigo());
		if (pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return this.lancamentoRepository.save(lancamento);
	}
	
}
