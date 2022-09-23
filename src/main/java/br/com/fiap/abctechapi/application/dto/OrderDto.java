package br.com.fiap.abctechapi.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderDto {
    private Long operatorId;
    private List<Long> assists;
    private OrderLocationDto start;
    private OrderLocationDto end;
}
