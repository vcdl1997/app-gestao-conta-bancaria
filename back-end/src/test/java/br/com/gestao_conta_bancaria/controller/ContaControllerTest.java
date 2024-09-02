package br.com.gestao_conta_bancaria.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.gestao_conta_bancaria.domain.entity.Conta;
import br.com.gestao_conta_bancaria.domain.entity.Titular;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroContaVO;
import br.com.gestao_conta_bancaria.domain.vo.create.CadastroTransferenciaVO;
import br.com.gestao_conta_bancaria.utils.DataUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ContaControllerTest extends BaseControllerTest{
	
	@BeforeEach
    void setup() {
    	clearAll();
    }

    @Test
    void testDevePesquisarPorContasEEncontrarUmResultado() throws Exception {
    	Titular titular = cadastrarTitularPessoaFisicaComDadosAleatorios();
    	Conta conta = cadastrarContaComDadosAleatorios(titular, new BigDecimal(100));
    	    	
    	this.mockMvc.perform(get("/contas")).andDo(print())
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
    		.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numero", Matchers.is(conta.getNumero())))
    	;
    }
    
    @Test
    void testDevePesquisarContasUtilizandoFiltros() throws Exception {
    	Titular titular = cadastrarTitularPessoaFisicaComDadosAleatorios();
    	Conta conta = cadastrarContaComDadosAleatorios(titular, new BigDecimal(100));
    	
    	String url = new StringBuilder("/contas?")
    		.append("idConta=" + conta.getId())
    		.append("&codigoAgencia=" + conta.getCodigoAgencia())
    		.append("&numeroConta=" + conta.getNumero())
    		.append("&nomeTitular=" + titular.getNome())
    		.append("&documentoIdentificacaoTitular=" + titular.getDocumentoIdentificacao())
    		.toString()
    	;
    	
    	this.mockMvc.perform(get(url)).andDo(print())
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
    		.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numero", Matchers.is(conta.getNumero())))
    	;
    }
    
    @Test
    void testDevePesquisarPeloIdDeUmaContaEmEspecifico() throws Exception {
    	Titular titular = cadastrarTitularPessoaFisicaComDadosAleatorios();
    	Conta conta = cadastrarContaComDadosAleatorios(titular, new BigDecimal(100));
    	    	
    	this.mockMvc.perform(get("/contas/" + conta.getId())).andDo(print())
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.numero", Matchers.is(conta.getNumero())))
    	;
    }
    
    @Test
    void testDeveCadastrarUmaContaComSucesso() throws Exception {
    	Titular titular = buildTitularPessoaFisicaComDadosAleatorios();
		Conta conta = buildContaComDadosAleatorios(titular, new BigDecimal(100));
    	CadastroContaVO vo = buildCadastroContaVO(titular, conta, "50.00");
    	
    	this.mockMvc.perform(post("/contas").contentType(APPLICATION_JSON).content(asJsonString(vo)))
    		.andExpect(MockMvcResultMatchers.status().isCreated())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.numero", Matchers.is(conta.getNumero())))
    	;
    }
    
    @Test
    void testDeveCadastrarUmaTransferenciaComSucesso() throws Exception {
    	Titular titularOrigem = cadastrarTitularPessoaFisicaComDadosAleatorios();
    	Conta contaOrigem = cadastrarContaComDadosAleatorios(titularOrigem, new BigDecimal(100));
    	Titular titularDestino = cadastrarTitularPessoaFisicaComDadosAleatorios();
    	Conta contaDestino = cadastrarContaComDadosAleatorios(titularDestino, new BigDecimal(50));
    	CadastroTransferenciaVO vo = buildCadastroTransferenciaVO(contaDestino, "10.00", DataUtils.format(LocalDate.now()));
    	String url = "/contas/" + contaOrigem.getId() + "/transferencias";
    	
    	this.mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(asJsonString(vo)))
    		.andExpect(MockMvcResultMatchers.status().isCreated())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.contaOrigem.numero", Matchers.is(contaOrigem.getNumero())))
    		.andExpect(MockMvcResultMatchers.jsonPath("$.contaDestino.numero", Matchers.is(contaDestino.getNumero())))
    	;
    }
}
