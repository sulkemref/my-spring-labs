package com.cydeo.spring05thymeleaf.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Product {
    private UUID id;
    @NotNull
    private Integer remainingQuantity;
    private Integer quantity;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String name;


}
