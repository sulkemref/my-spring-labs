package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddresses();

    void updateAddress(AddressDTO addressDTO);

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddressesStartsWith(String keyword);

    List<AddressDTO> getAddressesByCustomerId(Long id);

    List<AddressDTO> getAddressesByCustomerAndName(Long id,String name);

}
