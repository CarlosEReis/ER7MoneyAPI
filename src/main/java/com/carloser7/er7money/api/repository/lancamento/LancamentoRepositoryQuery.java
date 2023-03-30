package com.carloser7.er7money.api.repository.lancamento;

import java.util.List;

import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);
	
}
