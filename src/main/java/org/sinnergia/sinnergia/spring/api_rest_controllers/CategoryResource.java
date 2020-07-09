package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.business_controllers.CategoryController;
import org.sinnergia.sinnergia.spring.documents.Category;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.CategoryCreateDto;
import org.sinnergia.sinnergia.spring.dto.CategoryDto;
import org.sinnergia.sinnergia.spring.dto.CategoryUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(CategoryResource.CATEGORY)
public class CategoryResource {

    public static final String CATEGORY = "/category";
    public static final String ID = "/{id}";

    private CategoryController categoryController;

    @Autowired
    public CategoryResource(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @GetMapping
    public Flux<CategoryDto> getCategory() {
        return this.categoryController.readAll();
    }

    @PostMapping
    public Mono<Void> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto ){
        return this.categoryController.create(categoryCreateDto);
    }

    @DeleteMapping(value= ID)
    public Mono<Void> delete(@PathVariable String id){
        return this.categoryController.delete(id);
    }

    @PutMapping
    public Mono<Void> put(@RequestBody @Valid CategoryUpdateDto categoryUpdateDto){
        return this.categoryController.update(categoryUpdateDto);
    }
}
