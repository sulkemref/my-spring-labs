package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> retrieveOrderList();
}
