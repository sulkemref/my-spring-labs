package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.CustomerDTO;
import com.cydeo.lab08rest.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    CustomerDTO findById(Long id);
}
