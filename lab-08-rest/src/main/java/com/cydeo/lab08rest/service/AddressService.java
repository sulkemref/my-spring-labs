package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.AddressDTO;
import com.cydeo.lab08rest.dto.CustomerDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddresses();

    void updateAddress(Long addressId, AddressDTO addressDTO);

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddressesStartsWith(String keyword);

    List<AddressDTO> getAddressesByCustomerId(Long id);

    List<AddressDTO> getAddressesByCustomerAndName(Long id,String name);

}