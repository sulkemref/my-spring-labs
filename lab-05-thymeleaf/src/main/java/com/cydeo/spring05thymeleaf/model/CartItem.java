package com.cydeo.spring05thymeleaf.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CartItem {
    private Product product;
    private Integer quantity;
    private BigDecimal totalAmount;
}
