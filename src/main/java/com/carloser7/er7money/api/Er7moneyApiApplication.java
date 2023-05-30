package com.carloser7.er7money.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Er7moneyApiApplication {

	private static ApplicationContext APPLICATION_CONTEXT;
	
	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(Er7moneyApiApplication.class, args);
	}
	
	public static <T>T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
	// Criando a conta no Heroku
	// Deploy realizado com sucesso
}
