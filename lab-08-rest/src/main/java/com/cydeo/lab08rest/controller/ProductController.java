package com.cydeo.lab08rest.controller;

import com.cydeo.lab08rest.dto.ProductDTO;
import com.cydeo.lab08rest.dto.ProductRequest;
import com.cydeo.lab08rest.model.ResponseWrapper;
import com.cydeo.lab08rest.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllProducts(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getProductList(),HttpStatus.ACCEPTED));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateProduct (@RequestBody ProductDTO productDTO){
        productService.updateProduct(productDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Product is updated",productDTO,HttpStatus.CREATED));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProduct (@RequestBody ProductDTO productDTO){
        productService.createProduct(productDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Product is created",productDTO,HttpStatus.CREATED));
    }

    @PostMapping("/categoryandprice")
    public ResponseEntity<ResponseWrapper> getProductListByCategoryAndPrice (@RequestBody ProductRequest productRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getProductListByCategoryAndPrice(productRequest.getCategoryList(),productRequest.getPrice()),HttpStatus.CREATED));
    }



    @GetMapping("{name}")
    public ResponseEntity<ResponseWrapper> getProductByName(@PathVariable("name") String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Product is successfully retrieved",productService.getProductByName(name),HttpStatus.OK));
    }

    @GetMapping("/top3")
    public ResponseEntity<ResponseWrapper> getTop3Products(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getTop3ProductList(),HttpStatus.OK));
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<ResponseWrapper> getProductsByPrice(@PathVariable("price") BigDecimal price){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getCountProductListByPrice(price),HttpStatus.OK));
    }

    @GetMapping("/price/{price}/quantity/{quantity}")
    public ResponseEntity<ResponseWrapper> getProductsByPriceAndQuantity(@PathVariable("price") BigDecimal price,@PathVariable("quantity") int quantity){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getProductListByPriceAndQuantity(price,quantity),HttpStatus.OK));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseWrapper> getProductsByCategory(@PathVariable("id") Long id){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Products are successfully retrieved",productService.getProductListByCategory(id),HttpStatus.OK));
    }

}
