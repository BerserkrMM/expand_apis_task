package com.example.expand_apis_task.controller;

import com.example.expand_apis_task.dto.AddProductsDTO;
import com.example.expand_apis_task.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody AddProductsDTO addProductsDTO) {
        productService.add(addProductsDTO);
        return ResponseEntity.ok("Records added successfully");
    }
}
