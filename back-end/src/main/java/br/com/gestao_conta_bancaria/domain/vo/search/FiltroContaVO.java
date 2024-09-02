package br.com.gestao_conta_bancaria.domain.vo.search;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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
public class FiltroContaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Pattern(regexp = "^[0-9]$", message = "Deve ser numérico")
	private String idConta;
	
	@Pattern(regexp = "^[0-9]{3,5}$", message = "Deve ser numérico e possuir no mínimo 3 e máximo 5 caracteres")
	private String codigoAgencia;
	
	@Pattern(regexp = "^[0-9]{6,10}$", message = "Deve ser numérico e possuir no mínimo 6 e máximo 10 caracteres")
	private String numeroConta;
	
	@Length(min = 2, max = 200, message = "Deve possuir no mínimo 2 e máximo 200 caracteres")
	private String nomeTitular;
	
	@Length(min = 11, max = 14, message = "Deve possuir no mínimo 11 e máximo 14 caracteres")
	private String documentoIdentificacaoTitular;
	
}
