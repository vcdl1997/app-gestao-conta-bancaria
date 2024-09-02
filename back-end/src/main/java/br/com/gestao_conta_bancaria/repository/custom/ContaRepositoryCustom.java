package br.com.gestao_conta_bancaria.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gestao_conta_bancaria.domain.dto.ContaDTO;
import br.com.gestao_conta_bancaria.domain.vo.search.FiltroContaVO;

public interface ContaRepositoryCustom {
	
	 Page<ContaDTO> listarContas(final FiltroContaVO vo, final Pageable pageable);
	 
}
