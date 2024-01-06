package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.dto.AddProductsDTO;
import com.example.expand_apis_task.dto.ProductDTO;
import com.example.expand_apis_task.excaption.ProductAddException;
import com.example.expand_apis_task.excaption.ProductCreationException;
import com.example.expand_apis_task.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductDTO> getAll() {
        List<Object[]> list = new ArrayList<>();
        try {
            list = entityManager.createNativeQuery("SELECT id, entry_date, item_code, item_name, item_quantity, status FROM products")
                    .getResultList();
        } catch (Exception e) {
            throw new IllegalStateException("Something gone wrong with records fetching: " + e.getMessage());
        }

        return list
                .stream()
                .map(product -> new ProductDTO.ProductDTOBuilder()
                        .id(Long.parseLong(product[0].toString()))
                        .entryDate(product[1].toString())
                        .itemCode(product[2].toString())
                        .itemName(product[3].toString())
                        .itemQuantity(Long.parseLong(product[4].toString()))
                        .status(product[5].toString())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public void add(AddProductsDTO addProductsDTO) {
        try {
            createProductTableIfNotExists(addProductsDTO.getTable());
        } catch (Exception e) {
            throw new ProductCreationException("Problem with products table creation");
        }

        try {
            addProductsToProductsTable(addProductsDTO.getTable(), addProductsDTO.getRecords());
        } catch (Exception e) {
            throw new ProductAddException("Problem with insertion to products table");
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

    private void addProductsToProductsTable(String tableName, List<ProductDTO> productDTOs) {
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
