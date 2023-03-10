package com.cydeo.spring05thymeleaf;

import com.cydeo.spring05thymeleaf.bootstrap.DataGenerator;
import com.cydeo.spring05thymeleaf.repository.ProductRepository;
import com.cydeo.spring05thymeleaf.repository.impl.ProductRepositoryImpl;
import com.cydeo.spring05thymeleaf.service.CartService;
import com.cydeo.spring05thymeleaf.service.ProductService;
import com.cydeo.spring05thymeleaf.service.impl.CartServiceImpl;
import com.cydeo.spring05thymeleaf.service.impl.ProductServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Lab05ThymeleafApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Lab05ThymeleafApplication.class, args);

        ProductService productService = context.getBean(ProductServiceImpl.class);
        CartService cartService = context.getBean(CartServiceImpl.class);



        while (true){
            System.out.println(productService.listProduct());
            System.out.println(cartService.retrieveCartDetail());
            new Scanner(System.in).nextInt();
        }

    }

}
