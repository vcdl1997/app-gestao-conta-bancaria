package br.com.gestao_conta_bancaria.domain.vo.validation;

import static br.com.gestao_conta_bancaria.utils.DataUtils.isValidDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateAllowedValidator implements ConstraintValidator<DateAllowed, String> {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		
		if(isEmpty(value) || !isValidDate(value)) {
			context.buildConstraintViolationWithTemplate("Data inválida").addConstraintViolation();
			return false;
		}
		
		LocalDate atual = LocalDate.now();
		LocalDate dataInformada = LocalDate.parse(value, DATE_FORMATTER);
		long diferencaEntreDataAtualEDataInformada = DAYS.between(atual, dataInformada);
		
		if(diferencaEntreDataAtualEDataInformada < 0) {
			context.buildConstraintViolationWithTemplate("Data deve ser maior que a Atual").addConstraintViolation();
			return false;
		}
		
		return true;
	}

}
