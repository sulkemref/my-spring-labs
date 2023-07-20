package com.cydeo.lab08rest.entity;

import com.cydeo.lab08rest.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class Discount extends BaseEntity{
    private String name;
    private BigDecimal discount;
    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;

    //newly added
    private BigDecimal minimumAmount;
}
