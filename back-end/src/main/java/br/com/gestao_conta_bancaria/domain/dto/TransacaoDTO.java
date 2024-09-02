package br.com.gestao_conta_bancaria.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TransacaoDTO {
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate data;
	
	private String tipo;
	
	private BigDecimal valor;
}
