package com.fastlog.rastreamento.infrastructure.handler;

import com.fastlog.rastreamento.infrastructure.handler.exception.EncomendaNaoEncontradaException;
import java.net.URI;
import java.rmi.UnexpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    private ProblemDetail problemDetailBuilder(HttpStatus status, String title, String message) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create("https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/" + status.value()));
        problemDetail.setTitle(title);
        problemDetail.setDetail(message);
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ProblemDetail erroAoValidarPayloadExceptionHandler(MethodArgumentNotValidException ex) {
        return problemDetailBuilder(HttpStatus.BAD_REQUEST, "Os dados no payload não são válidos", ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ProblemDetail erroAoReceberRequisicaoExceptionHandler(HttpMessageNotReadableException ex) {

        final String message = ex.getMessage();
        final String simplifiedMessage = message != null && message.contains(":")
                ? message.split(":")[0]
                : "Erro ao processar a requisição";

        return problemDetailBuilder(HttpStatus.BAD_REQUEST, "Erro ao receber requisição", simplifiedMessage);
    }

    @ExceptionHandler(UnexpectedException.class)
    private ProblemDetail erroAoValidarPayloadExceptionHandler(UnexpectedException ex) {
        return problemDetailBuilder(HttpStatus.BAD_REQUEST, "Erro ao receber requisição", ex.getLocalizedMessage());
    }

    @ExceptionHandler(EncomendaNaoEncontradaException.class)
    private ProblemDetail encomendaNaoEncontradaExceptionHandler(EncomendaNaoEncontradaException ex) {
        return problemDetailBuilder(HttpStatus.BAD_REQUEST, "Encomenda não encontrada", ex.getMessage());
    }
}
