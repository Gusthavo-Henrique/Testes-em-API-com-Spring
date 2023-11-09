package br.com.gusthavo.entidade.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.gusthavo.services.exception.PessoaNãoEncontradaException;

@RestController
@ControllerAdvice
public class ExceptionHandlerCustomizado extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceçãoPadrão> exceptionParaTodos(Exception ex, WebRequest request) {
		ExceçãoPadrão err = new ExceçãoPadrão(new Date(), request.getDescription(false), ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PessoaNãoEncontradaException.class)
	public ResponseEntity<ExceçãoPadrão> exceptionPessoaNaoEncontrada(Exception ex, WebRequest request) {
		ExceçãoPadrão err = new ExceçãoPadrão(new Date(), request.getDescription(false), ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

}
