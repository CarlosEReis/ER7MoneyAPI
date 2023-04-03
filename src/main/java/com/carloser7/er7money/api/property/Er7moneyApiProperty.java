package com.carloser7.er7money.api.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("er7money")
public class Er7moneyApiProperty {

	private String origemPermitida = "http://localhost:8000";
	private final Seguranca seguranca = new Seguranca();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}
	
	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}
	
	public static class Seguranca {
		
		private boolean enablehttps;

		public boolean isEnablehttps() {
			return enablehttps;
		}

		public void setEnablehttps(boolean enablehttps) {
			this.enablehttps = enablehttps;
		}

	}

}
