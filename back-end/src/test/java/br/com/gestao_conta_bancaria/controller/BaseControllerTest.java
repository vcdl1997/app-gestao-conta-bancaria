package br.com.gestao_conta_bancaria.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.entity.Titular;
import br.com.gestao_conta_bancaria.domain.enumeration.TipoTitularEnum;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroContaVO;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroTransferenciaVO;
import br.com.gestao_conta_bancaria.repository.ContaRepository;
import br.com.gestao_conta_bancaria.repository.TitularRepository;
import br.com.gestao_conta_bancaria.repository.TransferenciaRepository;

public class BaseControllerTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TitularRepository titularRepository;
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	private Faker faker = new Faker();
	
	protected void clearAll() {
		transferenciaRepository.deleteAll();
		contaRepository.deleteAll();
		titularRepository.deleteAll();
	}
	
	protected Titular buildTitularPessoaFisicaComDadosAleatorios() {
		return Titular
			.builder()
			.nome(faker.name().firstName())
			.documentoIdentificacao(String.valueOf(faker.number().numberBetween(11111111111L, 99999999999L)))
			.dataCriacao(LocalDateTime.now())
			.tipo(TipoTitularEnum.PESSOA_FISICA)
			.build()
		;
	}
	
	protected Titular cadastrarTitularPessoaFisicaComDadosAleatorios() {
		Titular titular = titularRepository.save(buildTitularPessoaFisicaComDadosAleatorios());
		assertNotNull(titular);
		return titular;
	}
	
	protected Conta buildContaComDadosAleatorios(Titular titular, BigDecimal saldo) {
		return Conta
			.builder()
			.titular(titular)
			.codigoAgencia(faker.number().numberBetween(111, 999))
			.numero(faker.number().numberBetween(111111, 999999))
			.dataAbertura(LocalDateTime.now())
			.saldo(saldo)
			.build()
		;
	}
	
	protected Conta cadastrarContaComDadosAleatorios(Titular titular, BigDecimal saldo) {
		Conta conta = contaRepository.save(buildContaComDadosAleatorios(titular, saldo));
		assertNotNull(conta);
		return conta;
	}
	
	protected CadastroContaVO buildCadastroContaVO(Titular titular, Conta conta, String valorDeposito) {
		return CadastroContaVO
			.builder()
			.codigoAgencia(conta.getCodigoAgencia().toString())
			.numeroConta(conta.getNumero().toString())
			.nomeTitular(titular.getNome())
			.documentoIdentificacaoTitular(titular.getDocumentoIdentificacao())
			.valorDeposito(valorDeposito)
			.build()
		;
	}
	
	protected CadastroTransferenciaVO buildCadastroTransferenciaVO(Conta contaDestino, String valorATransferir, String dataATransferir) {
		return CadastroTransferenciaVO
			.builder()
			.codigoAgenciaDestino(contaDestino.getCodigoAgencia().toString())
			.numeroContaDestino(contaDestino.getNumero().toString())
			.valorATransferir(valorATransferir)
			.dataATransferir(dataATransferir)
			.build()
		;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
