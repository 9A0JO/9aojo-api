package br.com.fiap.abctechapi.controller;

import br.com.fiap.abctechapi.application.OrderApplication;
import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.OrderDto;
import br.com.fiap.abctechapi.application.dto.OrderResponseDto;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.model.Order;
import br.com.fiap.abctechapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderApplication orderApplication;
    public OrderController(@Autowired OrderApplication orderApplication
                           ) {
        this.orderApplication = orderApplication;
    }
    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Valid OrderDto orderDto, UriComponentsBuilder uriBuilder)  {
        OrderDto order = orderApplication.createOrder(orderDto);
        URI uri = uriBuilder.path("/order/operator/{operatorId}").buildAndExpand(order.getOperatorId()).toUri();
        return ResponseEntity.created(uri).body(order);
    }
    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<OrderResponseDto>> listOrdersByOperatorId(@PathVariable Long operatorId) {
        return ResponseEntity.ok(orderApplication.listOrdersByOperatorId(operatorId));
    }
    @GetMapping("/operator")
    public ResponseEntity<List<OrderResponseDto>> listOrdersOperator() {
        return ResponseEntity.ok(orderApplication.listOrdersOperator());
    }

    @GetMapping("/paginacao")
    public ResponseEntity<Page<Order>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao
    ) {
        Page<Order> lista = orderApplication.listOrdersOperatorPages(paginacao);
        return ResponseEntity.ok(lista);
    }
}
