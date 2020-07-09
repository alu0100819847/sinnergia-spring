package org.sinnergia.sinnergia.spring.dto;

public class CategoryUpdateDto extends CategoryCreateDto {

    String id;

    public CategoryUpdateDto() {
    }

    public CategoryUpdateDto(String categoryFamilyId, String name, String id) {
        super(categoryFamilyId, name);
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
        return "CategoryUpdateDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
