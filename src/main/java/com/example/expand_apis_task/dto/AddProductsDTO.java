package com.example.expand_apis_task.dto;

import java.util.List;

public class AddProductsDTO {
    private String table;
    private List<ProductDTO> records;

    public AddProductsDTO(String table, List<ProductDTO> records) {
        this.table = table;
        this.records = records;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddProductsDTO that)) return false;

        if (getTable() != null ? !getTable().equals(that.getTable()) : that.getTable() != null) return false;
        return getRecords() != null ? getRecords().equals(that.getRecords()) : that.getRecords() == null;
    }

    @Override
    public int hashCode() {
        int result = getTable() != null ? getTable().hashCode() : 0;
        result = 31 * result + (getRecords() != null ? getRecords().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddProductsDTO{" +
                "table='" + table + '\'' +
                ", records=" + records +
                '}';
    }
}
