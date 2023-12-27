package com.example.expand_apis_task.controller;

import com.example.expand_apis_task.model.Product;
import com.example.expand_apis_task.dto.ProductsDTO;
import com.example.expand_apis_task.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody ProductsDTO productsDTO) {
        productService.addProducts(productsDTO);
        return ResponseEntity.ok("Products added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
