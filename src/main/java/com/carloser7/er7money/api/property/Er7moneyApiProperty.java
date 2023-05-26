package com.carloser7.er7money.api.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("er7money")
@Component
public class Er7moneyApiProperty {

	private String origemPermitida = "http://localhost:8000";

	private final Seguranca seguranca = new Seguranca();

	private final Mail mail = new Mail();

	public Mail getMail() {
		return mail;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String originPermitida) {
		this.origemPermitida = originPermitida;
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

	public static class Mail {

		private String host;

		private Integer port;

		private String username;

		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
