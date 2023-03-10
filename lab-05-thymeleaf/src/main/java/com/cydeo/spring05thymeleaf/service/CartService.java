package com.cydeo.spring05thymeleaf.service;


import com.cydeo.spring05thymeleaf.model.Cart;
import com.cydeo.spring05thymeleaf.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    Cart addToCart(UUID productId, Integer quantity);
    boolean deleteFromCart(UUID productId);
    List<CartItem> retrieveCartDetail();



}
