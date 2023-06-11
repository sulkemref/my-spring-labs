package com.cydeo.lab08rest.service.impl;

import com.cydeo.lab08rest.dto.OrderDTO;
import com.cydeo.lab08rest.entity.Order;
import com.cydeo.lab08rest.mapper.MapperUtil;
import com.cydeo.lab08rest.repository.OrderRepository;
import com.cydeo.lab08rest.service.CartService;
import com.cydeo.lab08rest.service.CustomerService;
import com.cydeo.lab08rest.service.OrderService;
import com.cydeo.lab08rest.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MapperUtil mapperUtil;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, MapperUtil mapperUtil, CustomerService customerService, PaymentService paymentService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.mapperUtil = mapperUtil;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    @Override
    public List<OrderDTO> retrieveOrderList() {
        //retrieve all orders and return as a List of OrderDTO
        return orderRepository.findAll().stream()
                .map(order -> mapperUtil.convert(order,new OrderDTO())).collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        //look for the orderId inside the database and throw the exception
        Order order = orderRepository.findById(orderDTO.getId()).orElseThrow(
                () -> new RuntimeException("Order could not be found."));

        //then we need to check if the order fields are exist or not
        validateRelatedFieldsAreExist(orderDTO);

        return null;
    }

    private void validateRelatedFieldsAreExist(OrderDTO orderDTO) {
        //in this method we have 3 different service and make sure they have those fields
        //we will create service and existById method and verify

        if(!customerService.existById(orderDTO.getCustomerId())){
            throw new RuntimeException("Customer could not found");
        }

        if(!paymentService.existById(orderDTO.getPaymentId())){
            throw new RuntimeException("Payment could not found");
        }



    }
}
