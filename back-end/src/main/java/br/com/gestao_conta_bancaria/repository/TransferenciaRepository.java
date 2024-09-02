package br.com.gestao_conta_bancaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gestao_conta_bancaria.domain.dto.TransacaoDTO;
import br.com.gestao_conta_bancaria.domain.entity.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>{

	@Query("" + 
		"SELECT new br.com.gestao_conta_bancaria.domain.dto.TransacaoDTO("
		+ "		t.dataTransferencia AS data, "
		+ "		(CASE"
		+ "			WHEN (t.contaOrigem.codigoAgencia = :codigoAgencia AND t.contaOrigem.numero = :numeroConta) THEN 'Transferência' "
		+ "			ELSE 'Deposito' "
		+ "		END) AS tipo, "
		+ "		t.valor AS valor)"
		+ "FROM Transferencia t "
		+ "WHERE ( "
		+ "		t.contaOrigem.codigoAgencia = :codigoAgencia AND t.contaOrigem.numero = :numeroConta OR"
		+ "		t.contaDestino.codigoAgencia = :codigoAgencia AND t.contaDestino.numero = :numeroConta"
		+ ")"
	)
	List<TransacaoDTO> findExtratoByCodigoAgenciaAndNumeroConta(@Param("codigoAgencia") Integer codigoAgencia, @Param("numeroConta") Integer numeroConta);
	
}
