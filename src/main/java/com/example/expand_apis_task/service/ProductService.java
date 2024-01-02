package com.example.expand_apis_task.service;

import com.example.expand_apis_task.dto.AddProductsDTO;
import com.example.expand_apis_task.dto.ProductDTO;
//import com.example.expand_apis_task.model.Product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    void add(AddProductsDTO addProductsDTO);
}
