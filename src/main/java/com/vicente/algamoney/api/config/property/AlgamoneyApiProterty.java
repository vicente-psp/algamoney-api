package com.vicente.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProterty {

	@Getter @Setter
	private String originAllow = "http://localhost:8080";
	
	@Getter
	private final Seguranca seguranca = new Seguranca();

	@Getter @Setter
	public static class Seguranca {
		private boolean enableHttps;
	}
}
