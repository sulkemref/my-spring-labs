package com.cydeo.spring05thymeleaf.service.impl;


import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.repository.ProductRepository;
import com.cydeo.spring05thymeleaf.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public boolean productCreate(Product product){
        if(productRepository.findAll().stream().anyMatch(p->p.getName().equals(product.getName()))){
//          rewrite price and quantity if we have name
//           for (int i = 0 ; i<  productRepository.findAll().size(); i++){
//               if(product.getName().equals(productRepository.findAll().get(i).getName())){
//                   productRepository.findAll().get(i).setPrice(product.getPrice());
//                   productRepository.findAll().get(i).setRemainingQuantity(product.getRemainingQuantity());
//               }
//           }
            return false;
        }
        product.setId(UUID.randomUUID());
        product.setQuantity(product.getRemainingQuantity());
        return productRepository.save(product);
    }

    @Override
    public List<Product> listProduct() {

        return productRepository.findAll();

    }

    @Override
    public Product findProductById(UUID uuid){

        return  productRepository.findProductById(uuid);

    }

}
