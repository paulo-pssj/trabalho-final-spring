package br.com.shinigami.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class RegraDeNegocioException extends Exception {
    public RegraDeNegocioException(String message) {
        super(message);
    }
}
