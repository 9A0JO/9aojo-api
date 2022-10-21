package br.com.fiap.abctechapi.service.impl;

import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.handler.exception.MaxAssistsException;
import br.com.fiap.abctechapi.handler.exception.MinimumAssistsRequiredException;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.model.Order;
import br.com.fiap.abctechapi.repository.AssistanceRepository;
import br.com.fiap.abctechapi.repository.OrderRepository;
import br.com.fiap.abctechapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private AssistanceRepository assistanceRepository;
    private OrderRepository orderRepository;
    public OrderServiceImpl(
            @Autowired AssistanceRepository assistanceRepository,
            @Autowired OrderRepository orderRepository) {
        this.assistanceRepository = assistanceRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    public void saveOrder(Order order, List<Long> arrayAssists) {
        ArrayList<Assistance> assistances = new ArrayList<>();
        arrayAssists.forEach(i -> {
            Optional<Assistance> assistance = assistanceRepository.findById(i);
            if(!assistance.isPresent()) {
                throw new IdNotFoundException("Id invalid", "id não encontrado na base de dados");
            }
            assistances.add(assistance.get());
        });

        order.setAssists(assistances);

        if(!order.hasMinAssists()) {
            throw new MinimumAssistsRequiredException("Invalid Assists", "Necessario no minimo 1 assistência");
        }
        else if(order.exceedsMaxAssists()) {
            throw new MaxAssistsException("Invalid Assists", "Número máximo de assistências é 15");
        }

        orderRepository.save(order);
    }
    @Override
    public List<Order> listOrderByOperator(Long operatorId) {
        return orderRepository.getOrdersByOperatorId(operatorId);
    }

    @Override
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
    @Override
    public Page<Order> listOrdersPage(Pageable paginacao) {
        return orderRepository.findAll(paginacao);
    }


}
