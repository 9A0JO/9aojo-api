package br.com.fiap.abctechapi.service;

import br.com.fiap.abctechapi.application.dto.OrderResponseDto;
import br.com.fiap.abctechapi.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(Order order, List<Long> arrayAssists);
    List<Order> listOrderByOperator(Long operatorId);
    List<Order> listOrders();
}
