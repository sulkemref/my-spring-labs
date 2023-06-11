package com.cydeo.lab08rest.controller;

import com.cydeo.lab08rest.dto.OrderDTO;
import com.cydeo.lab08rest.dto.UpdateOrderDTO;
import com.cydeo.lab08rest.model.ResponseWrapper;
import com.cydeo.lab08rest.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> retrieveOrderList(){

        return ResponseEntity.ok(new ResponseWrapper("Orders are successfully retrieved.",
                orderService.retrieveOrderList(), HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateOrder(@Valid @RequestBody OrderDTO orderDTO){

        return ResponseEntity.ok(new ResponseWrapper("Order is successfully updated.",
                orderService.updateOrder(orderDTO), HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateOrderById(@PathVariable("id") Long id,@Valid @RequestBody UpdateOrderDTO updateOrderDTO){

        return ResponseEntity.ok(new ResponseWrapper("Order is successfully updated.",
                orderService.updateOrderById(id,updateOrderDTO), HttpStatus.OK));
    }


}
