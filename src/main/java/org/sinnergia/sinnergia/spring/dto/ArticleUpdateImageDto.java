package org.sinnergia.sinnergia.spring.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ArticleUpdateImageDto extends ArticleCreateImageDto {

    @NotNull
    String id;

    public ArticleUpdateImageDto(@NotNull String name, BigDecimal price, Integer stock, String description, MultipartFile file, @NotNull String id) {
        super(name, price, stock, description, file);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
