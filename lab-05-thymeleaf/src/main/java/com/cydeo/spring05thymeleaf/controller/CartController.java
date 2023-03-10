package com.cydeo.spring05thymeleaf.controller;

import com.cydeo.spring05thymeleaf.service.CartService;
import com.cydeo.spring05thymeleaf.service.impl.CartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class CartController {


    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String showCart(Model model){

        model.addAttribute("cartList", cartService.retrieveCartDetail());
        model.addAttribute("cartTotalAmount", CartServiceImpl.CART.getCartTotalAmount());

        return "/cart/show-cart";
    }
    @GetMapping("/addToCart/{productId}/{quantity}")
    public String addToCard(@PathVariable String productId, @PathVariable int quantity){
        cartService.addToCart(UUID.fromString(productId),quantity);
        return "redirect:/list";

    }

    @GetMapping("/delete/{productId}")
    public String deleteFromCart(@PathVariable String productId){
        cartService.deleteFromCart(UUID.fromString(productId));
        return "redirect:/cart";
    }
}
