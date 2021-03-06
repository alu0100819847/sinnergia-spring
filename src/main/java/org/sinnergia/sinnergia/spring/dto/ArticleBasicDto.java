package org.sinnergia.sinnergia.spring.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.sinnergia.sinnergia.spring.documents.Category;

import java.math.BigDecimal;

@JsonSerialize
public class ArticleBasicDto {

    private String id;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Category category;

    private byte[] file;

    public ArticleBasicDto() {
        // Empty
    }

    public ArticleBasicDto(String id, String name, BigDecimal price, Integer stock, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ArticleBasicDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}
