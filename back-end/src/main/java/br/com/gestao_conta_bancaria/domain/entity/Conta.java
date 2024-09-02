package br.com.gestao_conta_bancaria.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "TB_CONTA_BANCARIA", uniqueConstraints={
    @UniqueConstraint(columnNames = {"COD_AGENCIA", "NUMERO"})
})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Conta extends AbstractEntity<Long> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "COD_AGENCIA", length = 5)
	private Integer codigoAgencia; 
	
	@Column(name = "NUMERO", length = 10)
	private Integer numero; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TITULAR")
	@JsonIgnore
	private Titular titular;
	
	@Column(name = "SALDO")
	private BigDecimal saldo;
	
	@Column(name = "DATA_ABERTURA", updatable = false)
	private LocalDateTime dataAbertura;
}
