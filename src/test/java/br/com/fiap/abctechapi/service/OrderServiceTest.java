package br.com.fiap.abctechapi.service;

import br.com.fiap.abctechapi.handler.exception.MaxAssistsException;
import br.com.fiap.abctechapi.handler.exception.MinimumAssistsRequiredException;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.model.Order;
import br.com.fiap.abctechapi.repository.AssistanceRepository;
import br.com.fiap.abctechapi.repository.OrderRepository;
import br.com.fiap.abctechapi.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AssistanceRepository assistanceRepository;
    private OrderService orderService;

    @BeforeEach
    public void init() {
        orderService = new OrderServiceImpl(assistanceRepository, orderRepository);
        when(assistanceRepository
                .findById(any()))
                .thenReturn(Optional.of(new Assistance(1L, "Teste", "Teste description")));
    }

    @DisplayName("Obter um service :: success")
    @Test
    public void order_service_not_null() {
        Assertions.assertNotNull(orderService);
    }

    @DisplayName("Salvar uma Order em service :: success")
    @Test
    public void create_order_success() {
        Order newOrder = new Order();
        newOrder.setOperatorId(1234L);
        orderService.saveOrder(newOrder, generate_mocks_ids(5));
        verify(orderRepository, times(1)).save(newOrder);
    }

    @DisplayName("Salvar quantidade abaixo do minimo de assistence permitidas :: error")
    @Test
    public void create_order_error_minimum() {
        Order newOrder = new Order();
        newOrder.setOperatorId(1234L);
        Assertions.assertThrows(MinimumAssistsRequiredException.class, () -> orderService.saveOrder(newOrder, List.of()));
        verify(orderRepository, times(0)).save(newOrder);
    }

    @DisplayName("Salvar quantidade acima de assistence permitidas :: error")
    @Test
    public void create_order_error_maximum() {
        Order newOrder = new Order();
        newOrder.setOperatorId(1234L);
        Assertions.assertThrows(MaxAssistsException.class, () -> orderService.saveOrder(newOrder, generate_mocks_ids(20)));
        verify(orderRepository, times(0)).save(newOrder);
    }

    @DisplayName("Obter lista de Order de service :: success")
    @Test
    public void get_list_orders_service() {
        Order order = new Order();
        when(orderService.listOrders()).thenReturn(List.of(order));
        List<Order> listOrder = orderService.listOrders();
        Assertions.assertEquals( 1, listOrder.size());
    }

    @DisplayName("Obter lista paginada de Order de service :: success")
    @Test
    public void get_list_pages_orders_service() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> lista = new ArrayList<>();
        lista.add(order1);
        lista.add(order2);

        Page<Order> orderPages = new PageImpl<>(lista);
        when(orderService.listOrdersPage(Pageable.ofSize(1))).thenReturn(orderPages);
        Page<Order> result = orderService.listOrdersPage(Pageable.ofSize(1));
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getNumberOfElements());
        Assertions.assertEquals(2, result.getTotalElements());
    }


    @DisplayName("Obter lista de Order de service pelo operador id :: success")
    @Test
    public void get_order_service_by_operator_id() {
        Order order = new Order();
        order.setOperatorId(1234L);
        List<Order> list = new ArrayList<>();
        list.add(order);
        when(orderService.listOrderByOperator(1234L)).thenReturn(List.of(order));
        List<Order> listOrder = orderService.listOrderByOperator(1234L);
        Assertions.assertEquals(1, listOrder.size());
    }

    private List<Long> generate_mocks_ids(int number) {
        ArrayList<Long> arrayList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            arrayList.add(Integer.toUnsignedLong(i));
        }
        return arrayList;
    }
}
