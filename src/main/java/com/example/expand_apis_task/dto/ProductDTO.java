package com.example.expand_apis_task.dto;

public class ProductDTO {
    private Long id;
    private String entryDate;
    private String itemCode;
    private String itemName;
    private Long itemQuantity;
    private String status;

    public ProductDTO() {
    }

    public static class Builder {
        private Long id;
        private String entryDate;
        private String itemCode;
        private String itemName;
        private Long itemQuantity;
        private String status;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder entryDate(String entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder itemCode(String itemCode) {
            this.itemCode = itemCode;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder itemQuantity(Long itemQuantity) {
            this.itemQuantity = itemQuantity;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public ProductDTO build() {
            ProductDTO dto = new ProductDTO();
            dto.id = this.id;
            dto.entryDate = this.entryDate;
            dto.itemCode = this.itemCode;
            dto.itemName = this.itemName;
            dto.itemQuantity = this.itemQuantity;
            dto.status = this.status;
            return dto;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getEntryDate() != null ? !getEntryDate().equals(that.getEntryDate()) : that.getEntryDate() != null)
            return false;
        if (getItemCode() != null ? !getItemCode().equals(that.getItemCode()) : that.getItemCode() != null)
            return false;
        if (getItemName() != null ? !getItemName().equals(that.getItemName()) : that.getItemName() != null)
            return false;
        if (getItemQuantity() != null ? !getItemQuantity().equals(that.getItemQuantity()) : that.getItemQuantity() != null)
            return false;
        return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getEntryDate() != null ? getEntryDate().hashCode() : 0);
        result = 31 * result + (getItemCode() != null ? getItemCode().hashCode() : 0);
        result = 31 * result + (getItemName() != null ? getItemName().hashCode() : 0);
        result = 31 * result + (getItemQuantity() != null ? getItemQuantity().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", entryDate='" + entryDate + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", status='" + status + '\'' +
                '}';
    }
}
