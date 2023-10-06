package com.assembleia.app.votacao.exception.handler;

import com.assembleia.app.votacao.exception.DadoDuplicadoException;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponse> handlerNotFoundException(NotFoundException exception){
        return new ResponseEntity<>(new ErroResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErroResponse> handlerUnprocessableEntityException(UnprocessableEntityException exception){
        return new ResponseEntity<>(new ErroResponse(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DadoDuplicadoException.class)
    public ResponseEntity<ErroResponse> handlerDadoDuplicadoException(DadoDuplicadoException exception){
        return new ResponseEntity<>(new ErroResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
