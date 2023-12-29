package com.example.expand_apis_task.dto;

import java.util.List;

public class AddProductsDTO {
    private String table;
    private List<ProductDTO> records;

    public AddProductsDTO() {
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ProductDTO> getRecords() {
        return records;
    }

    public void setRecords(List<ProductDTO> records) {
        this.records = records;
    }
}
