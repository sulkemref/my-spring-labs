package com.cydeo.spring05thymeleaf.service.impl;

import com.cydeo.spring05thymeleaf.model.Cart;
import com.cydeo.spring05thymeleaf.model.CartItem;
import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.service.CartService;
import com.cydeo.spring05thymeleaf.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    public static Cart CART = new Cart(BigDecimal.ZERO,new ArrayList<>());

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addToCart(UUID productId, Integer quantity){
        //todo retrieve product from repository method
        Product product = productService.findProductById(productId);
        if(product.getRemainingQuantity()==0){
            return CART;
        }
        product.setRemainingQuantity(product.getRemainingQuantity()-1);
        //todo initialise cart item
        CartItem cartItem;
        if(CART.getCartItemList().stream().anyMatch(p -> p.getProduct().getId().equals(productId))){    // if we already have this product only increase quantity
            cartItem = CART.getCartItemList().stream()
                    .filter(p -> p.getProduct().getId()
                    .equals(productId)).findAny()
                    .orElseThrow();
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            //todo add to cart
            CART.getCartItemList().add(cartItem);
        }
        // calculate item total price
         cartItem.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        //todo calculate cart total amount
        calculateCartAmount();
        removeZeroRemainingQuantityProductsFromList();

        return CART;
    }

    @Override
    public boolean deleteFromCart(UUID productId){
        // remove item from cart
        CART.getCartItemList().remove
                (CART.getCartItemList().stream()
                        .filter(p -> p.getProduct().getId().equals(productId))
                        .findFirst().get());

        calculateCartAmount();
        return true;
    }

    @Override
    public List<CartItem> retrieveCartDetail() {
        return CART.getCartItemList();
    }

    private void calculateCartAmount(){
    CART.setCartTotalAmount(CART.getCartItemList().stream()
            .map(CartItem::getTotalAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO));

    }

    private void removeZeroRemainingQuantityProductsFromList(){
        productService.listProduct().removeIf(p -> p.getRemainingQuantity()==0);
    }
}
