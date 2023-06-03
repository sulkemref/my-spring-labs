package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO findById(Long id);

    List<CustomerDTO> findAll();

    void updateCustomer(CustomerDTO customerDTO);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO getByCustomerEmail(String email);
}
