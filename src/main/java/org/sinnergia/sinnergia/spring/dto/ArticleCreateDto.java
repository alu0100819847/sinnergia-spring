package org.sinnergia.sinnergia.spring.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ArticleCreateDto {

    @NotNull
    @Size(min = 1)
    private String name;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private String categoryId;

    public ArticleCreateDto() {
        this.name = "";
    }

    public ArticleCreateDto(@NotNull String name, BigDecimal price, Integer stock, String description, String categoryId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ArticleCreateDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
