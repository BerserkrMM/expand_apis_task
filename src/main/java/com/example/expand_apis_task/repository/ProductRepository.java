package com.example.expand_apis_task.repository;

import com.example.expand_apis_task.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom queries if needed
}
