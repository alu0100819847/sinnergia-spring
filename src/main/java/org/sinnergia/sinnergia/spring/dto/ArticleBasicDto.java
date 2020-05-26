package org.sinnergia.sinnergia.spring.dto;

import java.math.BigDecimal;

public class ArticleBasicDto {

    private String name;

    private BigDecimal price;

    private Integer stock;


    public ArticleBasicDto() {
        // Empty
    }

    public ArticleBasicDto(String name, BigDecimal price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
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


    @Override
    public String toString() {
        return "ArticleBasicDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
