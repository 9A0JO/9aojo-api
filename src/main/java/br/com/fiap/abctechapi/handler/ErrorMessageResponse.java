package br.com.fiap.abctechapi.handler;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
@Data
public class ErrorMessageResponse {
    private Integer statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
