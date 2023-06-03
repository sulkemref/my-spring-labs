package com.cydeo.lab08rest.service;

import com.cydeo.lab08rest.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductList();

    void updateProduct(ProductDTO productDTO);

    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> getProductListByCategoryAndPrice(List<Long> categoryIds, BigDecimal price);

    ProductDTO getProductByName(String name);

    List<ProductDTO> getTop3ProductList();

    Integer getCountProductListByPrice(BigDecimal price);

    List<ProductDTO> getProductListByPriceAndQuantity(BigDecimal price, int remainingQuantity);

    List<ProductDTO> getProductListByCategory(Long id);
}
