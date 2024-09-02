package br.com.gestao_conta_bancaria.repository.custom.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.gestao_conta_bancaria.domain.dto.ContaDTO;
import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.vo.search.FiltroContaVO;
import br.com.gestao_conta_bancaria.repository.criteria.ContaCriteria;
import br.com.gestao_conta_bancaria.repository.custom.ContaRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ContaRepositoryCustomImpl implements ContaRepositoryCustom{
	
	private final EntityManager entityManager;

	@Override
	public Page<ContaDTO> listarContas(final FiltroContaVO vo, final Pageable pageable) {
		CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ContaDTO> cQuery = cBuilder.createQuery(ContaDTO.class);
		ContaCriteria criteria = createContaCriteria(cQuery);
		selecionarColunas(cBuilder, cQuery, criteria, vo);
		
		cQuery
			.where(cBuilder.and(obterPredicate(cBuilder, criteria, vo)))
			.orderBy(cBuilder.asc(criteria.getRootConta().get("id")))
		;
		
		Long total = obterTotalDeLinhas(cBuilder, vo);
		TypedQuery<ContaDTO> typedQuery = createTypedQueryComPaginacao(cQuery, pageable);
		List<ContaDTO> resultado = typedQuery.getResultList();
		
		return new PageImpl<>(resultado, pageable, total);
	}
	
	public ContaCriteria createContaCriteria(final CriteriaQuery<?> cQuery) {
		ContaCriteria criteria = new ContaCriteria();
		
		criteria.setRootConta(cQuery.from(Conta.class));
		criteria.setJoinTitular(criteria.getRootConta().join("titular"));
		
		return criteria;
	}
	
	public void selecionarColunas(
		final CriteriaBuilder cBuilder, 
		final CriteriaQuery<ContaDTO> cQuery, 
		final ContaCriteria criteria,
		final FiltroContaVO vo
	) {
		List<Selection<?>> columns = new ArrayList<>();
		
		columns.add(criteria.getRootConta().get("id").as(Long.class).alias("id"));
		columns.add(criteria.getRootConta().get("codigoAgencia").as(Integer.class).alias("codigoAgencia"));
		columns.add(criteria.getRootConta().get("numero").as(Integer.class).alias("numero"));
		columns.add(criteria.getJoinTitular().get("nome").as(String.class).alias("nomeTitular"));
		columns.add(criteria.getJoinTitular().get("tipo").as(Integer.class).alias("tipoTitular"));
		columns.add(criteria.getJoinTitular().get("documentoIdentificacao").as(String.class).alias("documentoIdentificacaoTitular"));
		columns.add(criteria.getRootConta().get("dataAbertura").as(LocalDateTime.class).alias("dataAberturaConta"));
		columns.add(criteria.getRootConta().get("saldo").as(BigDecimal.class).alias("saldo"));
		
		if(isEmpty(vo.getIdConta())) {
			columns.remove(7);
		}
		
		cQuery.multiselect(columns.toArray(new Selection[0]));
	}
	
	public Predicate obterPredicate(
		final CriteriaBuilder cBuilder,
		final ContaCriteria criteria,
		final FiltroContaVO vo
	) {
		List<Predicate> predicates = new ArrayList<>();
		
        if (isNotEmpty(vo.getIdConta())) {
        	predicates.add(
        		cBuilder.equal(criteria.getRootConta().get("id"), Long.parseLong(vo.getIdConta()))
        	);
        }
        
        if (isNotEmpty(vo.getCodigoAgencia())) {
        	predicates.add(
        		cBuilder.equal(criteria.getRootConta().get("codigoAgencia"), Integer.parseInt(vo.getCodigoAgencia()))
        	);
        }
        
        if (isNotEmpty(vo.getNumeroConta())) {
        	predicates.add(
        		cBuilder.equal(criteria.getRootConta().get("numero"), Integer.parseInt(vo.getNumeroConta()))
        	);
        }
        
        if (isNotEmpty(vo.getNomeTitular())) {
        	predicates.add(
    		cBuilder.like(criteria.getJoinTitular().get("nome"), "%" + vo.getNomeTitular() + "%")
        	);
        }
        
        if (isNotEmpty(vo.getDocumentoIdentificacaoTitular())) {
        	predicates.add(
        		cBuilder.equal(criteria.getJoinTitular().get("documentoIdentificacao"), vo.getDocumentoIdentificacaoTitular())
        	);
        }

        return cBuilder.and(predicates.toArray(new Predicate[0]));
	}
	
	private TypedQuery<ContaDTO> createTypedQueryComPaginacao(final CriteriaQuery<ContaDTO> cQuery, final Pageable pageable){
		TypedQuery<ContaDTO> typedQuery = entityManager.createQuery(cQuery);
		int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);
        
        return typedQuery;
	}
	
	private Long obterTotalDeLinhas(final CriteriaBuilder cBuilder, final FiltroContaVO vo) {
		CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
		ContaCriteria criteria = createContaCriteria(cQuery);

		cQuery
			.select(cBuilder.count(criteria.getRootConta().get("id")))
			.where(cBuilder.and(obterPredicate(cBuilder, criteria, vo)))
		;
        
        return entityManager.createQuery(cQuery).getSingleResult();
    }
}
