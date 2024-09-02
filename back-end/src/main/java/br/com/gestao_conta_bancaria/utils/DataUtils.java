package br.com.gestao_conta_bancaria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtils {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static LocalDate parse(String data) {
		return LocalDate.parse(data, DATE_FORMATTER);
	}
	
	public static String format(LocalDate data) {
		return data.format(DATE_FORMATTER);
	}

	public static boolean isValidDate(String data) {
	    String formatacao = "yyyy-MM-dd";
	    
	    try {
	        SimpleDateFormat format = new SimpleDateFormat(formatacao);
	        format.setLenient(false);
	        format.parse(data);
	    } catch (ParseException | IllegalArgumentException e) {
	        return false;
	    }
	    
	    return true;
	}
}
