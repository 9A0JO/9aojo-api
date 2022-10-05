package br.com.fiap.abctechapi.application;

import br.com.fiap.abctechapi.application.dto.OrderDto;
import br.com.fiap.abctechapi.application.dto.OrderResponseDto;

import java.util.List;

public interface OrderApplication {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderResponseDto> listOrdersByOperatorId(Long operatorId);
    List<OrderResponseDto> listOrdersOperator();
}
