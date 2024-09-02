package br.com.gestao_conta_bancaria.repository.criteria;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.entity.Titular;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaCriteria {

	private Root<Conta> rootConta;
	
	private Join<Conta, Titular> joinTitular;
	
}
