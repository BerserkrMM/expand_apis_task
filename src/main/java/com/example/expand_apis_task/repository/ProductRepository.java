package com.example.expand_apis_task.repository;

import com.example.expand_apis_task.model.dto.ProductDTO;

import java.util.List;

public interface ProductRepository {

    List<ProductDTO> getAll();

    int createProductTableIfNotExists(String tableName);

    void addProductsToProductsTable(String tableName, List<ProductDTO> productDTOs);

}
