package com.sardeiro.minhasfinancas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MensagemService {
	
	@Value("${spring.application.name}")
	private String appName;
	
	

}
