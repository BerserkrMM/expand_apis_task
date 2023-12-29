package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.dto.AddProductsDTO;
import com.example.expand_apis_task.dto.ProductDTO;
import com.example.expand_apis_task.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void add(AddProductsDTO addProductsDTO) {
        try {
            createProductTableIfNotExists(addProductsDTO.getTable());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Problem with products table creation: " + e.getMessage());
        }

        try {
            addProductToProductsTable(addProductsDTO.getTable(), addProductsDTO.getRecords());
        } catch (Exception e) {
            throw new IllegalStateException("Problem with insertion to products table: " + e.getMessage());
        }
    }

    private void createProductTableIfNotExists(String tableName) throws Exception {
        entityManager.createNativeQuery(
                        "        CREATE TABLE IF NOT EXISTS " + tableName + " (     "
                                + "    id            BIGINT AUTO_INCREMENT NOT NULL,   "
                                + "    entry_date    VARCHAR(255)          NULL,       "
                                + "    item_code     VARCHAR(255)          NOT NULL,   "
                                + "    item_name     VARCHAR(255)          NULL,       "
                                + "    item_quantity INT                   NULL,       "
                                + "    status        VARCHAR(255)          NULL,       "
                                + "    CONSTRAINT pk_" + tableName + " PRIMARY KEY (id)"
                                + ");                                                  "

                )
                .executeUpdate();
    }

    private void addProductToProductsTable(String tableName, List<ProductDTO> productDTOs) {
        for (ProductDTO productDTO : productDTOs) {
            String insertQuery = "INSERT INTO " + tableName + " (entry_date, item_code, item_name, item_quantity, status) VALUES (?, ?, ?, ?, ?) ";

            entityManager.createNativeQuery(insertQuery)
                    .setParameter(1, productDTO.getEntryDate())
                    .setParameter(2, productDTO.getItemCode())
                    .setParameter(3, productDTO.getItemName())
                    .setParameter(4, productDTO.getItemQuantity())
                    .setParameter(5, productDTO.getStatus())
                    .executeUpdate();
        }
    }
}