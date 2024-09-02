package br.com.gestao_conta_bancaria.controller.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gestao_conta_bancaria.domain.dto.ErrorMessageDTO;
import br.com.gestao_conta_bancaria.domain.exception.BusinessException;
import br.com.gestao_conta_bancaria.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

	private final MessageSource messageSource;
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<ErrorMessageDTO> erros = new ArrayList<>();
				
		exception.getBindingResult().getFieldErrors().forEach(x -> {
			String mensagem = messageSource.getMessage(x, LocaleContextHolder.getLocale());			
			erros.add(new ErrorMessageDTO(x.getField(), mensagem));
		});

		return status(BAD_REQUEST).body(erros);
	}
	
	@ExceptionHandler(value = BindException.class)
	@ResponseStatus(BAD_REQUEST)
	public ResponseEntity<List<ErrorMessageDTO>> handleBindException(BindException exception) {
		List<ErrorMessageDTO> erros = new ArrayList<>();
		
		exception.getBindingResult().getFieldErrors().forEach(x -> {
			String mensagem = messageSource.getMessage(x, LocaleContextHolder.getLocale());			
			erros.add(new ErrorMessageDTO(x.getField(), mensagem));
		});

		return status(BAD_REQUEST).body(erros);
	}

	@ExceptionHandler(value = NotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public ResponseEntity<ErrorMessageDTO> handleNotFoundException(Exception exception) {
		ErrorMessageDTO erro = ErrorMessageDTO.builder().mensagem(exception.getMessage()).build();
		return status(NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(UNPROCESSABLE_ENTITY)
	public ResponseEntity<ErrorMessageDTO> HandleBusinessException(Exception exception) {
		ErrorMessageDTO erro = ErrorMessageDTO.builder().mensagem(exception.getMessage()).build();
		return status(UNPROCESSABLE_ENTITY).body(erro);
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorMessageDTO> HandleGlobalException(Exception exception) {
		ErrorMessageDTO erro = ErrorMessageDTO.builder().mensagem(exception.getMessage()).build();
		return status(INTERNAL_SERVER_ERROR).body(erro);
	}
}
