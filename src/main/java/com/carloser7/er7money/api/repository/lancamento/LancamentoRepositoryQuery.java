package com.carloser7.er7money.api.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carloser7.er7money.api.dto.LancamentoEstatisticaCategoria;
import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.projection.ResumoLancamento;
import com.carloser7.er7money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	
}
