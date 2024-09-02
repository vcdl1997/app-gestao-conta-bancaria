package br.com.gestao_conta_bancaria.service;

import static br.com.gestao_conta_bancaria.domain.dto.TransferenciaDTO.convertByTransferencia;
import static br.com.gestao_conta_bancaria.utils.TaxaTransferenciaCalculatorUtils.obterValorTransferencia;
import static java.lang.Integer.parseInt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gestao_conta_bancaria.domain.dto.ContaDTO;
import br.com.gestao_conta_bancaria.domain.dto.TransacaoDTO;
import br.com.gestao_conta_bancaria.domain.dto.TransferenciaDTO;
import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.entity.Titular;
import br.com.gestao_conta_bancaria.domain.entity.Transferencia;
import br.com.gestao_conta_bancaria.domain.enumeration.TipoTitularEnum;
import br.com.gestao_conta_bancaria.domain.exception.BusinessException;
import br.com.gestao_conta_bancaria.domain.exception.NotFoundException;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroContaVO;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroTransferenciaVO;
import br.com.gestao_conta_bancaria.domain.vo.search.FiltroContaVO;
import br.com.gestao_conta_bancaria.repository.ContaRepository;
import br.com.gestao_conta_bancaria.repository.TitularRepository;
import br.com.gestao_conta_bancaria.repository.TransferenciaRepository;
import br.com.gestao_conta_bancaria.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {
	
	private final ContaRepository contaRepository;
	
	private final TitularRepository titularRepository;
	
	private final TransferenciaRepository transferenciaRepository;
	
	private static final BigDecimal VALOR_MINIMO_DEPOSITO_ABERTURA_CONTA = new BigDecimal(50.00);
	
	public Page<ContaDTO> listarContas(final FiltroContaVO vo, final Pageable pageable){
		return contaRepository.listarContas(vo, pageable);
	}
	
	public ContaDTO obterContaPorId(final Long id){
		FiltroContaVO vo = FiltroContaVO.builder().idConta(id.toString()).build();
		Pageable pageable = Pageable.ofSize(1);
		
		Page<ContaDTO> resultadoPaginacao = contaRepository.listarContas(vo, pageable);
		List<ContaDTO> conteudo = resultadoPaginacao.getContent();
		
		if(conteudo.size() != 1) {
			throw new NotFoundException("Conta não encontrada");
		}
		
		return obterContaDTOComExtrato(conteudo.get(0));
	}
	
	public ContaDTO cadastrarConta(final CadastroContaVO vo){	
		if(contaRepository.existsByCodigoAgenciaAndNumero(parseInt(vo.getCodigoAgencia()), parseInt(vo.getNumeroConta()))) {
			throw new BusinessException("Já existe uma conta utilizando esse número nesta agência");
		}
		
		Conta conta = contaRepository.save(buildConta(vo));
		
		if(conta.getSaldo().compareTo(VALOR_MINIMO_DEPOSITO_ABERTURA_CONTA) <= -1) {
			throw new BusinessException("O valor mínimo para a abertura de contas é de R$ 50,00");
		}
		
		return obterContaPorId(conta.getId());
	}
	
	public TransferenciaDTO transferir(final Long idContaOrigem, final CadastroTransferenciaVO vo){
		Transferencia transferencia = buildTransferencia(idContaOrigem, vo);
		realizarSaque(transferencia);
		realizarDeposito(transferencia);
		return convertByTransferencia(transferenciaRepository.save(transferencia));
	}
	
	private Titular obterTitular(final CadastroContaVO vo) {
		Optional<Titular> titular = titularRepository.findByDocumentoIdentificacao(vo.getDocumentoIdentificacaoTitular());		
		return titular.isEmpty() ? titularRepository.save(buildTitular(vo)) : titular.get();
	}
	
	private Titular buildTitular(final CadastroContaVO vo) {
		return Titular
			.builder()
			.nome(vo.getNomeTitular())
			.documentoIdentificacao(vo.getDocumentoIdentificacaoTitular())
			.tipo(TipoTitularEnum.getByDocumentoIdentificacaoTitular(vo.getDocumentoIdentificacaoTitular()))
			.dataCriacao(LocalDateTime.now())
			.build()
		;
	}
	
	private Conta buildConta(final CadastroContaVO vo) {
		return Conta
			.builder()
			.codigoAgencia(Integer.valueOf(vo.getCodigoAgencia()))
			.numero(Integer.valueOf(vo.getNumeroConta()))
			.saldo(new BigDecimal(vo.getValorDeposito()))
			.titular(obterTitular(vo))
			.dataAbertura(LocalDateTime.now())
			.build()
		;
	}
	
	private Transferencia buildTransferencia(final Long idContaOrigem, final CadastroTransferenciaVO vo) {
		Conta contaOrigem = contaRepository.findById(idContaOrigem).orElseThrow(() -> new BusinessException("Conta de Origem inválida"));
		Conta contaDestino = contaRepository
			.findByCodigoAgenciaAndNumero(parseInt(vo.getCodigoAgenciaDestino()), parseInt(vo.getNumeroContaDestino()))
			.orElseThrow(() -> new BusinessException("Conta de Destino inválida"))
		;
		
		if(!transferenciaEntreContasEhPermitida(contaOrigem, contaDestino)) {
			throw new BusinessException("Operação inválida. Conta de Destino deve ser diferente da Conta de Origem");
		}

		return Transferencia
			.builder()
			.contaOrigem(contaOrigem)
			.contaDestino(contaDestino)
			.dataTransferencia(DataUtils.parse(vo.getDataATransferir()))
			.dataAgendamento(LocalDate.now())
			.valor(new BigDecimal(vo.getValorATransferir()))
			.build()
		;
	}
	
	private boolean transferenciaEntreContasEhPermitida(final Conta contaOrigem, final Conta contaDestino) {
		return !contaOrigem.getId().equals(contaDestino.getId());
	}
	
	private void realizarSaque(final Transferencia transferencia) {
		Conta contaOrigem = transferencia.getContaOrigem();
		BigDecimal valorASacar = obterValorTransferencia(transferencia.getDataAgendamento(), transferencia.getDataTransferencia(), transferencia.getValor());
		BigDecimal saldoAposSaque = contaOrigem.getSaldo().subtract(valorASacar);
		
		if(saldoAposSaque.signum() == -1) {
			throw new BusinessException("Saldo insuficiente");
		}
		
		contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorASacar));
		contaRepository.save(contaOrigem);
	}
	
	private void realizarDeposito(final Transferencia transferencia) {
		Conta contaDestino = transferencia.getContaDestino();
		contaDestino.setSaldo(contaDestino.getSaldo().add(transferencia.getValor()));
		contaRepository.save(contaDestino);
	}
	
	private ContaDTO obterContaDTOComExtrato(final ContaDTO dto) {
		List<TransacaoDTO> transacoes = transferenciaRepository.findExtratoByCodigoAgenciaAndNumeroConta(dto.getCodigoAgencia(), dto.getNumero());
		dto.setExtrato(transacoes);
		
		return dto;
	}
}
