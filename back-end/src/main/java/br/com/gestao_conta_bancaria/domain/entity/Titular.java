package br.com.gestao_conta_bancaria.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gestao_conta_bancaria.domain.converter.TipoTitularConverter;
import br.com.gestao_conta_bancaria.domain.enumeration.TipoTitularEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TB_TITULAR")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Titular extends AbstractEntity<Long> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NOME", nullable = false, length = 200)
	private String nome;
	
	@Column(name = "DOCUMENTO_IDENTIFICACAO", unique = true, updatable = false, nullable = false, length = 14)
	private String documentoIdentificacao;
	
	@Column(name = "TIPO_TITULAR", nullable = false)
	@Convert(converter = TipoTitularConverter.class)
	private TipoTitularEnum tipo;
	
	@Column(name = "DATA_CRIACAO", updatable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titular")
	private Set<Conta> contas;
}
