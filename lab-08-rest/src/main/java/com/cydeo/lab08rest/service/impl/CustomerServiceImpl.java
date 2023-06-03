package com.cydeo.lab08rest.service.impl;

import com.cydeo.lab08rest.dto.CustomerDTO;
import com.cydeo.lab08rest.entity.Customer;
import com.cydeo.lab08rest.mapper.MapperUtil;
import com.cydeo.lab08rest.repository.CustomerRepository;
import com.cydeo.lab08rest.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final MapperUtil mapperUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, MapperUtil mapperUtil) {
        this.customerRepository = customerRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(new Customer());
        return mapperUtil.convert(customer, new CustomerDTO());
    }
}
