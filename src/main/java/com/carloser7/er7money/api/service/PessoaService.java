package com.carloser7.er7money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloser7.er7money.api.model.Pessoa;
import com.carloser7.er7money.api.repository.PessoaRepository;
import com.carloser7.er7money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaSalva.setAtivo(pessoa.isAtivo()); // Forçando o setAtivo, pois o método acima COPYPROPERTIES não trabalha com a classe wrapper Boolean.

		return this.pessoaRepository.save(pessoaSalva);
	}

	protected Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository
				.findById(codigo)
				.orElseThrow(() -> new PessoaInexistenteOuInativaException());
		return pessoaSalva;
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoa = this.buscarPessoaPeloCodigo(codigo);
		pessoa.setAtivo(ativo);
		this.pessoaRepository.save(pessoa);
	}
	
	public void validaPessoa(Pessoa pessoa) {
		Pessoa pessoaLancamentoAtual = buscarPessoaPeloCodigo(pessoa.getCodigo());
		if (pessoaLancamentoAtual.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
}
