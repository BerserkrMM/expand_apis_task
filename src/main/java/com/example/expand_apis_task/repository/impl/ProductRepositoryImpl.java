package com.example.expand_apis_task.repository.impl;

import com.example.expand_apis_task.model.dto.ProductDTO;
import com.example.expand_apis_task.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Object[]> l = entityManager.createNativeQuery(
                        "SELECT id, entry_date, item_code, item_name, item_quantity, status FROM products")
                .getResultList();

        return l.stream()
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

    @Override
    public int createProductTableIfNotExists(String tableName) {
        return entityManager.createNativeQuery(
                        "       CREATE TABLE IF NOT EXISTS " + tableName + " (        "
                                + "    id            BIGINT AUTO_INCREMENT NOT NULL,     "
                                + "    entry_date    VARCHAR(255)          NULL,         "
                                + "    item_code     VARCHAR(255)          NOT NULL,     "
                                + "    item_name     VARCHAR(255)          NULL,         "
                                + "    item_quantity INT                   NULL,         "
                                + "    status        VARCHAR(255)          NULL,         "
                                + "    CONSTRAINT pk_" + tableName + " PRIMARY KEY (id));"

                )
                .executeUpdate();
    }

    @Transactional
    @Override
    public void addProductsToProductsTable(String tableName, List<ProductDTO> productDTOs) {
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
