package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.exception.ProductGetAllException;
import com.example.expand_apis_task.model.dto.AddProductsDTO;
import com.example.expand_apis_task.model.dto.ProductDTO;
import com.example.expand_apis_task.exception.ProductAddException;
import com.example.expand_apis_task.exception.TableCreationException;
import com.example.expand_apis_task.repository.ProductRepository;
import com.example.expand_apis_task.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final Logger LOG = LoggerFactory.getLogger("TTT");
    @PersistenceContext
    private EntityManager entityManager;

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAll() {
        try {
            return productRepository.getAll();
        } catch (Exception e) {
            throw new ProductGetAllException("Something gone wrong with records fetching: ", e);
        }
    }

    @Transactional
    @Override
    public void add(AddProductsDTO addProductsDTO) throws Exception {
        try {
            productRepository.createProductTableIfNotExists(addProductsDTO.getTable());
        } catch (Exception e) {
            throw new TableCreationException("Problem with products table creation", e);
        }

        try {
            productRepository.addProductsToProductsTable(addProductsDTO.getTable(), addProductsDTO.getRecords());
        } catch (Exception e) {
            throw new ProductAddException("Problem with insertion to products table",e);
        }
    }
}
