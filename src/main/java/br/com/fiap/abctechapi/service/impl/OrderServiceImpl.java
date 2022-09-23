package br.com.fiap.abctechapi.service.impl;

import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.model.Order;
import br.com.fiap.abctechapi.repository.AssistanceRepository;
import br.com.fiap.abctechapi.repository.OrderRepository;
import br.com.fiap.abctechapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void saveOrder(Order order, List<Long> arrayAssists) throws Exception {
        ArrayList<Assistance> assistances = new ArrayList<>();
        arrayAssists.forEach(i -> {
            Assistance assistance = assistanceRepository.findById(i).orElseThrow();
            assistances.add(assistance);
        });

        order.setAssists(assistances);

        if(!order.hasMinAssists()) {
            throw new Exception();
        }
        else if(order.exceedsMaxAssists()) {
            throw new Exception();
        }

        orderRepository.save(order);
    }

    @Override
    public List<Order> listOrderByOperator(Long operatorId) {
        return null;
    }
}
