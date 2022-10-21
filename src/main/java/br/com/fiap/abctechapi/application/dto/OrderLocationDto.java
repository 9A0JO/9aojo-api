package br.com.fiap.abctechapi.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderLocationDto {
    private Double latitude;
    private Double longitude;
    private Date datetime;
    public Date getDatetime() {
        if(this.datetime == null) {
            return new Date();
        }
        return datetime;
    }



}
