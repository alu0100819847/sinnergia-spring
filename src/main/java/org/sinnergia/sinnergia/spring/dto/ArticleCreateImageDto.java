package org.sinnergia.sinnergia.spring.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ArticleCreateImageDto extends ArticleCreateDto{

    private MultipartFile file;

    public ArticleCreateImageDto() {
    }

    public ArticleCreateImageDto(@NotNull String name, BigDecimal price, Integer stock, String description, String categoryId, MultipartFile file) {
        super(name, price, stock, description, categoryId);
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ArticleCreateImageDto{" +
                "file=" + file +
                '}';
    }
}
