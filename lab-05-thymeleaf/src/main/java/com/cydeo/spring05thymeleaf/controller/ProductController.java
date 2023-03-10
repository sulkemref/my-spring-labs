package com.cydeo.spring05thymeleaf.controller;

import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create-product")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "/product/create-product";
    }

    @GetMapping ("/create-form")
    public String createProductForm(){
        return "redirect:/create-product";
    }


    @PostMapping("/confirm-product")
    public String confirm(@ModelAttribute("product")@Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/product/create-product";
        }
        productService.productCreate(product);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("listProduct",productService.listProduct());
        return "/product/list";
    }

}
