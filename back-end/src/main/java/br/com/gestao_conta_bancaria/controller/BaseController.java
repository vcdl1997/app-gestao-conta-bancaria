package br.com.gestao_conta_bancaria.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;

public abstract class BaseController {

	protected HttpHeaders buildHttpHeaders(final String uri, final Number id) {
		HttpHeaders headers = new HttpHeaders(); 
        headers.setLocation(URI.create(uri + id));
        return headers;
	}
	
}
