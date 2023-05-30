package com.carloser7.er7money.api.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("er7money")
@Component
public class Er7moneyApiProperty {

	private final Seguranca seguranca = new Seguranca();

	private final Mail mail = new Mail();

	private final S3 s3 = new S3();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public Mail getMail() {
		return mail;
	}
	
	public S3 getS3() {
		return s3;
	}

	public static class Seguranca {

		private boolean enableHttps;
		private String origemPermitida = "http://localhost:8000";;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

		public String getOrigemPermitida() {
			return origemPermitida;
		}

		public void setOrigemPermitida(String origemPermitida) {
			this.origemPermitida = origemPermitida;
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

	public static class S3 {

		private String accessKeyId;
		private String secretAccessKey;

		public String getAccessKeyId() {
			return accessKeyId;
		}

		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		public String getSecretAccessKey() {
			return secretAccessKey;
		}

		public void setSecretAccessKey(String secretAccessKey) {
			this.secretAccessKey = secretAccessKey;
		}

	}
}
