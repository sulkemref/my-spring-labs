package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.DiscountDTO;

import java.util.List;

public interface DiscountService {

    List<DiscountDTO> findAllDiscounts();

    void updateDiscount(DiscountDTO discountDTO);

    DiscountDTO createDiscount(DiscountDTO discountDTO);

    DiscountDTO getDiscountByName(String name);
}
