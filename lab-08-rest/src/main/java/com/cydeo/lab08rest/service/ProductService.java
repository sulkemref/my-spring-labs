package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDTO> retrieveProductList();

    ProductDTO updateProduct(ProductDTO productDTO);

    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> retrieveAllProductByCategoryAndPrice(List<Long> categoryList, BigDecimal price);
}