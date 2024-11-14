package com.fastlog.rastreamento.infrastructure.handler.exception;

public class EncomendaNaoEncontradaException extends RuntimeException {
    public EncomendaNaoEncontradaException(String message) {
        super(message);
    }
}
