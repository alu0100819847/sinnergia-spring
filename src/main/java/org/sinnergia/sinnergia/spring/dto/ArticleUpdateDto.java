package org.sinnergia.sinnergia.spring.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ArticleUpdateDto extends ArticleCreateDto {

    String id;

    public ArticleUpdateDto() {
    }

    public ArticleUpdateDto(String id, @NotNull String name, BigDecimal price, Integer stock, String description, String categoryId) {
        super(name, price, stock, description, categoryId);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ArticleUpdateDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
