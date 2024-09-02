package br.com.gestao_conta_bancaria.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TB_TRANSFERENCIA")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Transferencia extends AbstractEntity<Long> implements Serializable{

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_CONTA_ORIGEM", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Conta contaOrigem; 
	
	@JoinColumn(name = "ID_CONTA_DESTINO", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Conta contaDestino; 
	
	@Column(name = "VALOR")
	private BigDecimal valor;
	
	@Column(name = "DATA_TRANSFERENCIA")
	private LocalDate dataTransferencia;
	
	@Column(name = "DATA_AGENDAMENTO")
	private LocalDate dataAgendamento;
	
}
