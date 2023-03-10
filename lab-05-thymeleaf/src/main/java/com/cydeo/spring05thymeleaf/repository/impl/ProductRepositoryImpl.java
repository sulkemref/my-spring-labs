package com.cydeo.spring05thymeleaf.repository.impl;

import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class ProductRepositoryImpl implements ProductRepository {
    public static List<Product> PRODUCT_LIST = new ArrayList<>();
    @Override
    public boolean save(Product product){
        return PRODUCT_LIST.add(product);
    }

    @Override
    public Product findProductById(UUID productId) {
        return PRODUCT_LIST.stream().filter(product -> product.getId().
                toString().equals(productId.toString())).findFirst().orElseThrow();
    }

    @Override
    public List<Product> findAll() {
        return PRODUCT_LIST;
    }
}
