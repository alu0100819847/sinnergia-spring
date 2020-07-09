package org.sinnergia.sinnergia.spring.business_controllers;

import org.sinnergia.sinnergia.spring.documents.Category;
import org.sinnergia.sinnergia.spring.dto.CategoryCreateDto;
import org.sinnergia.sinnergia.spring.dto.CategoryDto;
import org.sinnergia.sinnergia.spring.dto.CategoryUpdateDto;
import org.sinnergia.sinnergia.spring.dto.SubcategoriesDto;
import org.sinnergia.sinnergia.spring.exceptions.ConflictException;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
import org.sinnergia.sinnergia.spring.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<CategoryDto> readAll() {
        return this.categoryRepository.findAll()
                .map(category -> {
                    List<SubcategoriesDto> subcategories = category.getSubcategories().stream()
                            .map(subcategory -> new SubcategoriesDto( subcategory.getId(), subcategory.getName())).collect(Collectors.toList());
                    return new CategoryDto(category.getId(), category.getName(), subcategories);
                });
    }

    public Mono<Void> create(CategoryCreateDto categoryCreateDto) {
        if(categoryCreateDto.getCategoryFamilyId() != null){
            Mono<Category> categoryMono = this.categoryRepository.findById(categoryCreateDto.getCategoryFamilyId())
                .map(categoryFamily -> {
                    Category category = new Category(categoryCreateDto.getName());
                    categoryFamily.addSubcategory(category);
                    return categoryFamily;
                });
            return this.categoryRepository.saveAll(categoryMono).then();
        }
        Category category = new Category(categoryCreateDto.getName());
        return this.categoryRepository.save(category).then();
    }

    public Mono<Void> delete(String categoryId) {
        return this.categoryRepository.deleteById(categoryId);
    }

    public Mono<Void> update(CategoryUpdateDto categoryUpdateDto) {
        Mono<Category> categoryMono = this.categoryRepository.findById(categoryUpdateDto.getId())
                .map(category -> {
                    category.setName(categoryUpdateDto.getName());
                    return category;
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Category Not Found")));
        return this.categoryRepository.saveAll(categoryMono).then();
    }
}
