package br.com.gestao_conta_bancaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestao_conta_bancaria.domain.entity.Titular;

@Repository
public interface TitularRepository extends JpaRepository<Titular, Long>{

	Optional<Titular> findByDocumentoIdentificacao(String documentoIdentificacao);
	
}
