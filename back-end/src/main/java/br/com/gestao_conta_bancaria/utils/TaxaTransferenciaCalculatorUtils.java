package br.com.gestao_conta_bancaria.utils;

import static br.com.gestao_conta_bancaria.domain.enumeration.TaxaTransferenciaEnum.getTaxaTransferenciaEnumByQtdDias;
import static java.lang.Math.toIntExact;
import static java.time.temporal.ChronoUnit.DAYS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxaTransferenciaCalculatorUtils {
	
	public static BigDecimal obterValorTransferencia(final LocalDate dataAgendamento, LocalDate dataTransferencia, final BigDecimal valorTransferencia) {
		Integer qtdDias = toIntExact(DAYS.between(dataAgendamento, dataTransferencia));
		BigDecimal taxa = BigDecimal.valueOf(getTaxaTransferenciaEnumByQtdDias(qtdDias).getValorTaxa());
		BigDecimal valorAEmbutir = valorTransferencia.multiply(taxa).divide(new BigDecimal(100));
		return valorTransferencia.add(valorAEmbutir);
	}
}
