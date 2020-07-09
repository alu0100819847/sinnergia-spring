package org.sinnergia.sinnergia.spring.dto;

public class SubcategoriesDto {

    String id;
    String name;

    public SubcategoriesDto() {
    }

    public SubcategoriesDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
