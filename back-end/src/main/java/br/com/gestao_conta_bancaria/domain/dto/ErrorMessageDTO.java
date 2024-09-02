package br.com.gestao_conta_bancaria.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String campo;
	
	private String mensagem;
	
}