package org.sinnergia.sinnergia.spring.dto;

import org.sinnergia.sinnergia.spring.documents.Category;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {

    private String id;

    private String name;

    private List<SubcategoriesDto> subcategories;

    public CategoryDto() {
    }

    public CategoryDto(String id ,String name, List<SubcategoriesDto> subcategories) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSubcategories(List<SubcategoriesDto> subcategories) {
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubcategoriesDto> getSubcategories() {
        return subcategories;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "name='" + name + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
