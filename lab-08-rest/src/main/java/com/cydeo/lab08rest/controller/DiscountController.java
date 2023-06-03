package com.cydeo.lab08rest.controller;

import com.cydeo.lab08rest.dto.DiscountDTO;
import com.cydeo.lab08rest.model.ResponseWrapper;
import com.cydeo.lab08rest.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllDiscounts(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Discounts are successfully retrieved",discountService.findAllDiscounts(),HttpStatus.ACCEPTED));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateDiscount (@RequestBody DiscountDTO discountDTO){
        discountService.updateDiscount(discountDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Discount is updated",discountDTO,HttpStatus.CREATED));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createDiscount (@RequestBody DiscountDTO discountDTO){
        discountService.createDiscount(discountDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Discount is created",discountDTO,HttpStatus.CREATED));
    }

    @GetMapping("{name}")
    public ResponseEntity<ResponseWrapper> getDiscountByName(@PathVariable("name") String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Discount is successfully retrieved",discountService.getDiscountByName(name),HttpStatus.OK));
    }

}
