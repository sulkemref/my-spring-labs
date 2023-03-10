package com.cydeo.spring05thymeleaf.bootstrap;

import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.repository.ProductRepository;
import com.cydeo.spring05thymeleaf.repository.impl.ProductRepositoryImpl;
import com.cydeo.spring05thymeleaf.service.ProductService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DataGenerator {

    ProductService productService;

    public DataGenerator(ProductService productService) {
        this.productService = productService;
    }

    public void createFakeProduct(){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(new Faker().random().nextInt(1,1000)));
        product.setName(new Faker().pokemon().name());
        product.setRemainingQuantity(new Faker().random().nextInt(5,100));
        productService.productCreate(product);
    }

}
