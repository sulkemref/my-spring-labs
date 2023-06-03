package com.cydeo.lab08rest.service.impl;

import com.cydeo.lab08rest.dto.ProductDTO;
import com.cydeo.lab08rest.entity.Category;
import com.cydeo.lab08rest.entity.Product;
import com.cydeo.lab08rest.mapper.MapperUtil;
import com.cydeo.lab08rest.repository.CategoryRepository;
import com.cydeo.lab08rest.repository.ProductRepository;
import com.cydeo.lab08rest.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ProductDTO> getProductList() {

        return productRepository.findAll().stream()
                .map(obj -> mapperUtil.convert(obj, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {

        Long productId = productDTO.getId();

        Product product = mapperUtil.convert(productDTO, new Product());

        productRepository.findById(productId).ifPresent(dbProduct -> {
            dbProduct.setPrice(product.getPrice());
            dbProduct.setName(product.getName());
            dbProduct.setQuantity(product.getQuantity());
            dbProduct.setRemainingQuantity(product.getRemainingQuantity());
            dbProduct.setCategoryList(product.getCategoryList());

            productRepository.save(dbProduct);
        });
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productRepository.save(mapperUtil.convert(productDTO, new Product()));
        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductListByCategoryAndPrice(List<Long> categoryIds, BigDecimal price) {
        return productRepository.retrieveProductListByCategory(categoryIds,price).stream()
                .map(obj -> mapperUtil.convert(obj,new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductByName(String name) {
        return mapperUtil.convert(productRepository.findFirstByName(name),new ProductDTO());
    }

    @Override
    public List<ProductDTO> getTop3ProductList() {
        return productRepository.findTop3ByOrderByPriceDesc().stream()
                .map(obj -> mapperUtil.convert(obj, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getCountProductListByPrice(BigDecimal price) {
        return productRepository.countProductByPriceGreaterThan(price);
    }

    @Override
    public List<ProductDTO> getProductListByPriceAndQuantity(BigDecimal price, int remainingQuantity) {
        return productRepository.retrieveProductListGreaterThanPriceAndLowerThanRemainingQuantity(price,remainingQuantity)
                .stream()
                .map(obj -> mapperUtil.convert(obj, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductListByCategory(Long id) {
       Category category = categoryRepository.findById(id).orElse(new Category());
       return productRepository.findAllByCategoryListContaining(category).stream()
                .map(obj -> mapperUtil.convert(obj, new ProductDTO()))
                .collect(Collectors.toList());
    }
}
