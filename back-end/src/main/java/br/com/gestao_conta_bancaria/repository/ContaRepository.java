package br.com.gestao_conta_bancaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.repository.custom.ContaRepositoryCustom;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, ContaRepositoryCustom{
	
	Optional<Conta> findByCodigoAgenciaAndNumero(Integer codigoAgencia, Integer numero);
	
	boolean existsByCodigoAgenciaAndNumero(Integer codigoAgencia, Integer numero);
	
}
