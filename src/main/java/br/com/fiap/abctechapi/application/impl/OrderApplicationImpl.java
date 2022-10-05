package br.com.fiap.abctechapi.application.impl;

import br.com.fiap.abctechapi.application.OrderApplication;
import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.OrderDto;
import br.com.fiap.abctechapi.application.dto.OrderLocationDto;
import br.com.fiap.abctechapi.application.dto.OrderResponseDto;
import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.model.Order;
import br.com.fiap.abctechapi.model.OrderLocation;
import br.com.fiap.abctechapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderApplicationImpl implements OrderApplication {
    private final OrderService orderService;
    public OrderApplicationImpl(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }
    @Override
    public OrderDto createOrder(OrderDto orderDto)  {
        Order order = new Order();
        order.setOperatorId(orderDto.getOperatorId());
        order.setStartOrderLocation(getOrderLocationFromOrderLocationDto(orderDto.getStart()));
        order.setEndOrderLocation(getOrderLocationFromOrderLocationDto(orderDto.getEnd()));
        this.orderService.saveOrder(order, orderDto.getAssists());
        return orderDto;
    }
    private OrderLocation getOrderLocationFromOrderLocationDto(OrderLocationDto orderLocationDto) {
        OrderLocation orderLocation = new OrderLocation();
        orderLocation.setLatitude(orderLocationDto.getLatitude());
        orderLocation.setLongitude(orderLocationDto.getLongitude());
        orderLocation.setDate(orderLocationDto.getDatetime());
        return orderLocation;
    }

    @Override
    public List<OrderResponseDto> listOrdersByOperatorId(Long operatorId) {
        List<Order> ordersByOperator = orderService.listOrderByOperator(operatorId);
        if (ordersByOperator.size() == 0) {
            throw new IdNotFoundException("OperatorId invalid", "operator_id não encontrado na base de dados");
        }

        return orderService.listOrderByOperator(operatorId).stream().map(
                (order) -> new OrderResponseDto(order.getId(), order.getOperatorId(),
                        order.getAssists().stream().map(
                                this::mapAssistsToDto).collect(Collectors.toList()),
                                mapOrderLocationToDto(order.getStartOrderLocation()),
                                mapOrderLocationToDto(order.getEndOrderLocation())
                        )).collect(Collectors.toList());        
    }

    private OrderLocationDto mapOrderLocationToDto(OrderLocation orderLocation) {
        OrderLocationDto orderLocationDto = new OrderLocationDto();
        orderLocationDto.setLatitude(orderLocation.getLatitude());
        orderLocationDto.setLongitude(orderLocation.getLongitude());
        orderLocationDto.setDatetime(orderLocation.getDate());
        return orderLocationDto;
    }

    private AssistDto mapAssistsToDto(Assistance assistance) {
        return new AssistDto(assistance);
    }

    @Override
    public List<OrderResponseDto> listOrdersOperator() {
        return orderService.listOrders().stream().map(
                (order) -> new OrderResponseDto(order.getId(), order.getOperatorId(),
                        order.getAssists().stream().map(
                                this::mapAssistsToDto).collect(Collectors.toList()),
                        mapOrderLocationToDto(order.getStartOrderLocation()),
                        mapOrderLocationToDto(order.getEndOrderLocation())
                )).collect(Collectors.toList());
    }
}
