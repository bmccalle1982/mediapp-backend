package com.mitocode.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice//Trabaje como una especie de interpretador
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorRecord> handleDefaultExceptions(Exception ex, WebRequest webRequest)
    {
        CustomErrorRecord customErrorRecord = new CustomErrorRecord(LocalDateTime.now(),
                                                                   ex.getMessage(),
                                                                   webRequest.getDescription(false));

        return new ResponseEntity<>(customErrorRecord, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Es la mejor forma de usar , debido a que le defines como brindar la información
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorRecord> handleModelNotFoundException(ModelNotFoundException exception, WebRequest request)
    {
        CustomErrorRecord customErrorRecord = new CustomErrorRecord(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(customErrorRecord, HttpStatus.NOT_FOUND);
    }
    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorRecord> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,WebRequest request)
    {
        CustomErrorRecord customErrorRecord = new CustomErrorRecord(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(customErrorRecord, HttpStatus.BAD_REQUEST);
    }*/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField().concat(":").concat(error.getDefaultMessage()))
                .collect(Collectors.joining(","));

        CustomErrorRecord customErrorRecord = new CustomErrorRecord(LocalDateTime.now(), message,
                request.getDescription(false));
        return new ResponseEntity<>(customErrorRecord, HttpStatus.BAD_REQUEST);
    }

    //Desde Spring Boot 3
    //Primera forma
    /*@ExceptionHandler(ModelNotFoundException.class)//Permite definir una estructura de mensaje de errores
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex,WebRequest webRequest)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Model not found exception");
        problemDetail.setType(URI.create(webRequest.getDescription(false)));
        problemDetail.setProperty("extra1", "extra-value");
        problemDetail.setProperty("extra2", 33);
        return problemDetail;
    }*/

    //Segunda forma
    /*
    @ExceptionHandler(ModelNotFoundException.class)
    public ErrorResponse handleModelNotFoundException(ModelNotFoundException ex, WebRequest webRequest)
    {
        return ErrorResponse.builder(ex,HttpStatus.NOT_FOUND, ex.getMessage())
                .title("Model Not Found Exception")
                .type(URI.create(webRequest.getDescription(false)))
                .property("extra1", "extra-value")
                .property("extra2", 33)
                .build();
    }*/

}
