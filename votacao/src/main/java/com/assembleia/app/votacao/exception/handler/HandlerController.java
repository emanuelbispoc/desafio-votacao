package com.assembleia.app.votacao.exception.handler;

import com.assembleia.app.votacao.exception.DadoDuplicadoException;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request
    ) {
        List<ErroValidacaoResponse.ErroCampo> erros = new ArrayList<>();
        ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()
                .forEach(error -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                    erros.add(new ErroValidacaoResponse.ErroCampo(fieldName, errorMessage));
        });
        return new ResponseEntity<>(new ErroValidacaoResponse("Campos inválidos.", erros), HttpStatus.BAD_REQUEST);
    }
}
