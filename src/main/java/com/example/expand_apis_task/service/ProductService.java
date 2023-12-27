package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.Product;
import com.example.expand_apis_task.dto.ProductsDTO;
import com.example.expand_apis_task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void addProducts(ProductsDTO productsDTO) {
        // Save products dynamically
        // Create table if not exist
    }

    public List<Product> getAllProducts() {
        // Retrieve all products
        return null;
    }
}
