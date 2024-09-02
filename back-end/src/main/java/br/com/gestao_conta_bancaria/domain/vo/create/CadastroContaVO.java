package br.com.gestao_conta_bancaria.domain.vo.create;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class CadastroContaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[0-9]{3,5}$", message = "Deve ter no mínimo 3 e no máximo 5 números")
	private String codigoAgencia;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[0-9]{6,10}$", message = "Deve ter no mínimo 6 e no máximo 10 números")
	private String numeroConta;
	
	@NotNull
	@NotEmpty
	@Length(min = 2, max = 200)
	private String nomeTitular;
	
	@NotNull
	@NotEmpty
	@Length(min = 11, max = 14)
	private String documentoIdentificacaoTitular;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^(\\d){2,12}\\.(\\d){2}$", message = "Deve estar no formato decimal, sendo separado por ponto, conforme o exemplo: “50.00”")
	private String valorDeposito;
	
}
