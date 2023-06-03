package com.cydeo.lab08rest.controller;

import com.cydeo.lab08rest.dto.CustomerDTO;
import com.cydeo.lab08rest.model.ResponseWrapper;
import com.cydeo.lab08rest.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllCustomers(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address are successfully retrieved",customerService.findAll(),HttpStatus.ACCEPTED));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateCustomer (@RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(customerDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Customer is updated",customerDTO,HttpStatus.CREATED));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createCustomer (@RequestBody CustomerDTO customerDTO){
        customerService.createCustomer(customerDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Customer is created",customerDTO,HttpStatus.CREATED));
    }

    @GetMapping("{email}")
    public ResponseEntity<ResponseWrapper> getByCustomerEmail(@PathVariable("email") String email){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Customer is successfully retrieved",customerService.getByCustomerEmail(email),HttpStatus.OK));
    }
}
