package com.carloser7.er7money.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.carloser7.er7money.api.Er7moneyApiApplication;
import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.storage.S3;


public class LancamentoAnexoListener {

	@PostLoad
	public void postLoad(Lancamento lancamento) {
		if (StringUtils.hasText(lancamento.getAnexo())) {
			S3 s3 = Er7moneyApiApplication.getBean(S3.class);
			lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
		}
	}
}
