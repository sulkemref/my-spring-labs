package com.cydeo.lab08rest.controller;

import com.cydeo.lab08rest.dto.AddressDTO;
import com.cydeo.lab08rest.model.ResponseWrapper;
import com.cydeo.lab08rest.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllAddresses(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address are successfully retrieved",addressService.getAddresses(),HttpStatus.ACCEPTED));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateAddress (@RequestBody AddressDTO addressDTO){
        addressService.updateAddress(addressDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address is updated",addressDTO,HttpStatus.CREATED));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createAddress(@RequestBody AddressDTO addressDTO){
        addressService.createAddress(addressDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address is created",addressDTO,HttpStatus.CREATED));
    }

    @GetMapping("startsWith/{keyword}")
    public ResponseEntity<ResponseWrapper> getAllAddresses(@PathVariable("keyword") String keyword){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address are successfully retrieved",addressService.getAddressesStartsWith(keyword),HttpStatus.OK));
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<ResponseWrapper> getAllAddressesByCustomerId(@PathVariable("id") Long id){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address are successfully retrieved",addressService.getAddressesByCustomerId(id),HttpStatus.OK));
    }

    @GetMapping("customer/{id}/name/{name}")
    public ResponseEntity<ResponseWrapper> getAllAddressesByCustomerId(@PathVariable("id") Long id, @PathVariable("name") String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Address are successfully retrieved",addressService.getAddressesByCustomerAndName(id,name),HttpStatus.OK));
    }


}
