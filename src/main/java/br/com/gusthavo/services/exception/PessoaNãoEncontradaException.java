package br.com.gusthavo.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PessoaNãoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public PessoaNãoEncontradaException(String msg) {
		super(msg);
	}
	
}
