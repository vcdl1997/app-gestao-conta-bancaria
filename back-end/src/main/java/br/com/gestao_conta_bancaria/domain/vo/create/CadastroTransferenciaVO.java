package br.com.gestao_conta_bancaria.domain.vo.create;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.gestao_conta_bancaria.domain.vo.validation.DateAllowed;
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
public class CadastroTransferenciaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[0-9]{3,5}$", message = "Deve ter no mínimo 3 e no máximo 5 números")
	private String codigoAgenciaDestino;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[0-9]{6,10}$", message = "Deve ter no mínimo 6 e no máximo 10 números")
	private String numeroContaDestino;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^(\\d){2,12}\\.(\\d){2}$", message = "Deve estar no formato decimal, sendo separado por ponto, conforme o exemplo: “50.00”")
	private String valorATransferir;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Deve estar no formato: “YYYY-MM-DD”")
	@DateAllowed
	private String dataATransferir;
	
}
