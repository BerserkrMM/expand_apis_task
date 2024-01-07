package com.example.expand_apis_task.controller;

import com.example.expand_apis_task.model.dto.AddProductsDTO;
import com.example.expand_apis_task.model.dto.ProductDTO;
import com.example.expand_apis_task.model.dto.base.ResponseDTO;
import com.example.expand_apis_task.model.dto.base.ResponseDTOFactory;
import com.example.expand_apis_task.service.ProductService;
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

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<String>> addProducts(@RequestBody AddProductsDTO addProductsDTO) throws Exception {
        productService.add(addProductsDTO);
        return ResponseEntity.ok(ResponseDTOFactory.getOkResponseDto("Records added successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll() {
        return ResponseEntity.ok(ResponseDTOFactory.getOkResponseDto(productService.getAll()));
    }
}
