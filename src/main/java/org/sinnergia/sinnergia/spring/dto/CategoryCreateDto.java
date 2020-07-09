package org.sinnergia.sinnergia.spring.dto;

public class CategoryCreateDto {
    private String categoryFamilyId;
    private String name;

    public CategoryCreateDto() {
    }

    public CategoryCreateDto(String categoryFamilyId, String name) {
        this.categoryFamilyId = categoryFamilyId;
        this.name = name;
    }

    public String getCategoryFamilyId() {
        return categoryFamilyId;
    }

    public void setCategoryFamilyId(String categoryFamilyId) {
        this.categoryFamilyId = categoryFamilyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryCreateDto{" +
                "categoryFamilyId='" + categoryFamilyId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
