package br.com.gestao_conta_bancaria.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum TipoTitularEnum {	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Juridica");
	
	private Integer codigo;
	private String descricao;
	
	private static final Map<Integer, TipoTitularEnum> LOOKUP = new HashMap<>();
	
	private static final int QTD_DIGITOS_CPF = 11;
	private static final int QTD_DIGITOS_CNPJ = 14;

    static {
        for (TipoTitularEnum status : TipoTitularEnum.values()) {
        	LOOKUP.put(status.getCodigo(), status);
        }
    }
	
	private TipoTitularEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static TipoTitularEnum getById(final Integer id) {
		return LOOKUP.get(id);
	}
	
	public static TipoTitularEnum getByDocumentoIdentificacaoTitular(final String documentoIdentificacaoTitular) {		
		switch (documentoIdentificacaoTitular.length()) {
			case QTD_DIGITOS_CPF:
				return PESSOA_FISICA;
			
			case QTD_DIGITOS_CNPJ:
				return PESSOA_JURIDICA;

			default:
				throw new IllegalArgumentException("Tipo de Titular inválido");
		}
	}
}
