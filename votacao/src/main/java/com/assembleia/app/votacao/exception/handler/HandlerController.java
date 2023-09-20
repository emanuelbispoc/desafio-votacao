package com.assembleia.app.votacao.exception.handler;

import com.assembleia.app.votacao.exception.NotFoundException;
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
}
