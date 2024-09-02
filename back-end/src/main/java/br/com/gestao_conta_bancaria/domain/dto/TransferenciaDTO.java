package br.com.gestao_conta_bancaria.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.entity.Transferencia;
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
public class TransferenciaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private ContaDTO contaOrigem;
	
	private ContaDTO contaDestino;
	
	private BigDecimal valorATransferir;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataARealizarATransferencia;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataAgendamentoTransferencia;
	
	
	public static TransferenciaDTO convertByTransferencia(Transferencia transferencia) {
		Conta contaOrigem = transferencia.getContaOrigem();
		Conta contaDestino = transferencia.getContaDestino();
		
		return TransferenciaDTO
			.builder()
			.id(transferencia.getId())
			.contaOrigem(ContaDTO.builder().codigoAgencia(contaOrigem.getCodigoAgencia()).numero(contaOrigem.getNumero()).build())
			.contaDestino(ContaDTO.builder().codigoAgencia(contaDestino.getCodigoAgencia()).numero(contaDestino.getNumero()).build())
			.dataARealizarATransferencia(transferencia.getDataTransferencia())
			.dataAgendamentoTransferencia(transferencia.getDataAgendamento())
			.valorATransferir(transferencia.getValor())
			.build()
		;
	}
	
}
