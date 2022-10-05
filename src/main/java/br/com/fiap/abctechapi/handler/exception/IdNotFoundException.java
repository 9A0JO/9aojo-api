package br.com.fiap.abctechapi.handler.exception;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

@Getter
public class IdNotFoundException extends EntityNotFoundException {
    private String description;
    public IdNotFoundException(String message, String description) {
        super(message);
        this.description = description;
    }
}
