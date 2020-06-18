package org.sinnergia.sinnergia.spring.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ArticleCreateImageDto {

    @NotNull
    private String name;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private MultipartFile file;

    public ArticleCreateImageDto() {
        this.name = "";
    }

    public ArticleCreateImageDto(@NotNull String name, BigDecimal price, Integer stock, MultipartFile file, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.file = file;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArticleCreateImageDto(@NotNull String name, BigDecimal price, Integer stock, String description, MultipartFile file) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.file = file;
    }
}
