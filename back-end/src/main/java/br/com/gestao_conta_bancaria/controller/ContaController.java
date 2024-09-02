package br.com.gestao_conta_bancaria.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_conta_bancaria.domain.dto.ContaDTO;
import br.com.gestao_conta_bancaria.domain.dto.TransferenciaDTO;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroContaVO;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroTransferenciaVO;
import br.com.gestao_conta_bancaria.domain.vo.search.FiltroContaVO;
import br.com.gestao_conta_bancaria.service.ContaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController extends BaseController{
	
	private final ContaService contaService;
	
	@GetMapping
	@ResponseStatus(OK)
	public ResponseEntity<Page<ContaDTO>> listarContas(final @Valid FiltroContaVO vo, final Pageable pageable) {
		return ok(contaService.listarContas(vo, pageable));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(OK)
	public ResponseEntity<ContaDTO> obterContaPorId(final @PathVariable Long id) {
		return ok(contaService.obterContaPorId(id));
	}

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	@ResponseStatus(CREATED)
	public ResponseEntity<ContaDTO> cadastrarConta(final @Valid @RequestBody(required = true) CadastroContaVO vo) {
		ContaDTO dto = contaService.cadastrarConta(vo);
		HttpHeaders headers = buildHttpHeaders("/contas/", dto.getId());
		return new ResponseEntity<ContaDTO>(dto, headers, CREATED);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/{id}/transferencias")
	@ResponseStatus(CREATED)
	public ResponseEntity<TransferenciaDTO> cadastrarTransferencia(final @PathVariable Long id, final @Valid @RequestBody CadastroTransferenciaVO vo) {
		TransferenciaDTO dto = contaService.transferir(id, vo);
		HttpHeaders headers = buildHttpHeaders("/contas/" + id + "/transferencias", dto.getId());
		return new ResponseEntity<TransferenciaDTO>(dto, headers, CREATED);
	}
	
}
