package br.com.gestao_conta_bancaria.domain.enumeration;

import br.com.gestao_conta_bancaria.domain.exception.BusinessException;
import lombok.Getter;

@Getter
public enum TaxaTransferenciaEnum {
	IMEDIATA(0, 0, 2.5f),
	INTERVALO_10_DIAS(1, 10, 0f),
	INTERVALO_20_DIAS(11, 20, 8.2f),
	INTERVALO_30_DIAS(21, 30, 6.9f),
	INTERVALO_40_DIAS(31, 40, 4.7f),
	INTERVALO_50_DIAS(41, 50, 1.7f);
	
	private Integer qtdMinimaDias;
	private Integer qtdMaximaDias;
	private Float valorTaxa;
	
	private TaxaTransferenciaEnum(Integer qtdMinimaDias, Integer qtdMaximaDias, Float valorTaxa) {
		this.qtdMinimaDias = qtdMinimaDias;
		this.qtdMaximaDias = qtdMaximaDias;
		this.valorTaxa = valorTaxa;
	}
	
	public static TaxaTransferenciaEnum getTaxaTransferenciaEnumByQtdDias(final Integer qtdDias) {
		for (TaxaTransferenciaEnum taxa : TaxaTransferenciaEnum.values()) {
        	if(qtdDias >= taxa.getQtdMinimaDias() && qtdDias <= taxa.getQtdMaximaDias()) {
        		return taxa;
        	}
        }
		
		throw new BusinessException("O Intervalo de dias informado excede ao limite 50 dias");
	}
}
