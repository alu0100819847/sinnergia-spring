package org.sinnergia.sinnergia.spring.dto;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

public class ArticleCreateDto implements Serializable {

    @NotNull
    private String name;

    private BigDecimal price;

    private Integer stock;


    public ArticleCreateDto() {
    }

    public ArticleCreateDto(String name, BigDecimal price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
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


    @Override
    public String toString() {
        return "ArticleCreateDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
