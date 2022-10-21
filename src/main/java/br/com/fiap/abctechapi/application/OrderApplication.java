package br.com.fiap.abctechapi.application;

import br.com.fiap.abctechapi.application.dto.OrderDto;
import br.com.fiap.abctechapi.application.dto.OrderResponseDto;
import br.com.fiap.abctechapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderApplication {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderResponseDto> listOrdersByOperatorId(Long operatorId);
    List<OrderResponseDto> listOrdersOperator();
    Page<Order> listOrdersOperatorPages(Pageable pageable);
}
