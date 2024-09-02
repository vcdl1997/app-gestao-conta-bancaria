package br.com.gestao_conta_bancaria.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.gestao_conta_bancaria.domain.enumeration.TipoTitularEnum;
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
public class ContaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Integer codigoAgencia;
	
	private Integer numero;
	
	private String nomeTitular;
	
	private String tipoTitular;
	
	private String documentoIdentificacaoTitular;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataAberturaConta;
	
	private BigDecimal saldo;
	
	private List<TransacaoDTO> extrato;

	public ContaDTO(Long id, Integer codigoAgencia, Integer numero, String nomeTitular, Integer tipoTitular,
			String documentoIdentificacaoTitular, LocalDateTime dataAberturaConta) {
		this.id = id;
		this.codigoAgencia = codigoAgencia;
		this.numero = numero;
		this.nomeTitular = nomeTitular;
		this.tipoTitular = TipoTitularEnum.getById(tipoTitular).getDescricao();
		this.documentoIdentificacaoTitular = documentoIdentificacaoTitular;
		this.dataAberturaConta = dataAberturaConta;
	}

	public ContaDTO(Long id, Integer codigoAgencia, Integer numero, String nomeTitular, Integer tipoTitular,
			String documentoIdentificacaoTitular, LocalDateTime dataAberturaConta, BigDecimal saldo) {
		this.id = id;
		this.codigoAgencia = codigoAgencia;
		this.numero = numero;
		this.nomeTitular = nomeTitular;
		this.tipoTitular = TipoTitularEnum.getById(tipoTitular).getDescricao();
		this.documentoIdentificacaoTitular = documentoIdentificacaoTitular;
		this.dataAberturaConta = dataAberturaConta;
		this.saldo = saldo;
	}
}
