package br.com.fiap.abctechapi.repository;

import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Obter lista de Order de repository :: success")
    @Test
    public void get_list_orders_repository() {
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<Order> listOrder = orderRepository.findAll();
        Assertions.assertEquals(1, listOrder.size());
    }

    @DisplayName("Obter Order paginada de repository :: success")
    @Test
    public void get_list_pages_orders_repository() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        Page<Order> orderPages = new PageImpl<>(orders);
        when(orderRepository.findAll(Pageable.ofSize(1))).thenReturn(orderPages);
        Page<Order> result = orderRepository.findAll(Pageable.ofSize(1));
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getNumberOfElements());
        Assertions.assertEquals(2, result.getTotalElements());
    }

    @DisplayName("Obter lista vazia de Order de repository :: success")
    @Test
    public void get_list_empty_orders_repository() {
        when(orderRepository.findAll()).thenReturn(List.of());
        List<Order> listOrder = orderRepository.findAll();
        Assertions.assertEquals(0, listOrder.size());
    }

    @DisplayName("Obter lista de Order de repository pelo operador id :: success")
    @Test
    public void get_order_repository_by_operator_id() {
        Order order = new Order();
        order.setOperatorId(1234L);
        List<Order> list = new ArrayList<>();
        list.add(order);
        when(orderRepository.getOrdersByOperatorId(1234L)).thenReturn(List.of(order));
        List<Order> listOrder = orderRepository.getOrdersByOperatorId(1234L);
        Assertions.assertEquals(1, listOrder.size());
    }

    @DisplayName("Obter lista de Order de repository pelo operador id :: error")
    @Test
    public void get_order_repository_by_operator_id_error() {
        when(orderRepository.getOrdersByOperatorId(1L)).thenThrow(IdNotFoundException.class);
        Assertions.assertThrows(IdNotFoundException.class, () -> orderRepository.getOrdersByOperatorId(1L));
        try {
            orderRepository.getOrdersByOperatorId(1L);
        }
        catch (Exception e) {
            Assertions.assertEquals(IdNotFoundException.class, e.getClass());
        }
    }

    @DisplayName("Salvando uma Order em repository :: success")
    @Test
    public void create_order_success() {
        Order order = new Order();
        order.setOperatorId(1234L);
        orderRepository.save(order);
        verify(orderRepository, times(1)).save(order);
    }



}
